package com.dting.show.server.tasks;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.dting.show.server.conditions.MonitorBatchCondition;
import com.dting.show.server.constant.RedisKeyUtil;
import com.dting.show.server.service.MessageCpuSnapshotService;
import com.dting.show.server.service.MessageMemorySnapshotService;
import com.dting.show.server.utils.ScheduledTaskManagement;
import com.dting.show.server.utils.SpringUtil;
import com.dting.show.server.vos.monitoring.MemoryDataVo;
import com.dting.show.server.vos.monitoring.SystemCpuDataVo;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * *************************************************<br/>
 * 内存数据刷新任务<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/30 10:01
 */
public class CpuDataRefreshTask implements TimerTask {

    /**
     * 线程Id生成器
     */
    private final static AtomicInteger THREAD_ID = new AtomicInteger(0);

    /**
     * 机器的核心数
     */
    private final static int CORE_SIZE = Math.min(Runtime.getRuntime().availableProcessors(), 4);

    /**
     * 执行线程池
     */
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(CORE_SIZE, CORE_SIZE * 2, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024), r -> {
        Thread thread = new Thread(r);
        thread.setName(String.format("CpuDataRefreshTask-%s", THREAD_ID.incrementAndGet()));
        return thread;
    }, new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 本次内存查询的条件
     */
    private final MonitorBatchCondition monitorBatchCondition;

    /**
     * 会话id
     */
    private final String sessionId;

    /**
     * 字符串 redis操作模板
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * cpu数据服务对象
     */
    private final MessageCpuSnapshotService messageCpuSnapshotService;

    public CpuDataRefreshTask(MonitorBatchCondition monitorBatchCondition, String sessionId) {
        this.monitorBatchCondition = monitorBatchCondition;
        this.sessionId = sessionId;
        this.stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        this.messageCpuSnapshotService = SpringUtil.getBean(MessageCpuSnapshotService.class);
    }

    /**
     * 这里只需要根据条件将数据查出来 排好序 然后放到redis中  list 中 然后判断自己的监听是否还存在，存在就将自己重新加入到定时队列，不存在就结束
     *
     * @param timeout 到期任务
     * @throws Exception 异常信息
     */
    @Override
    public void run(Timeout timeout) throws Exception {
        POOL_EXECUTOR.execute(() -> {
            try {
                //获取内存数据
                SystemCpuDataVo systemCpuDataVo = messageCpuSnapshotService.cpuMonitoring(this.monitorBatchCondition, false);
                String dtingCpuCacheKey = RedisKeyUtil.dtingCpuCacheFormat(this.sessionId);
                if (CollUtil.isNotEmpty(systemCpuDataVo.getSystemCpuDataList())) {
                    //缓存到redis中
                    stringRedisTemplate.opsForList().leftPush(dtingCpuCacheKey, JSON.toJSONString(systemCpuDataVo));
                }
                //设置过期时间为2分钟  对数据续约
                stringRedisTemplate.expire(dtingCpuCacheKey, 120, TimeUnit.SECONDS);
            } finally {
                //判断redis
                String sessionActiveKey = RedisKeyUtil.sessionActiveKeyFormat(this.sessionId);
                Boolean hasKey = stringRedisTemplate.hasKey(sessionActiveKey);
                if (hasKey != null && hasKey) {
                    MonitorBatchCondition monitorBatchConditionCopy = new MonitorBatchCondition();
                    BeanUtil.copyProperties(monitorBatchCondition, monitorBatchConditionCopy);
                    monitorBatchConditionCopy.setStartTime(monitorBatchConditionCopy.getEndTime());
                    monitorBatchConditionCopy.setEndTime(System.currentTimeMillis());
                    //重新加载任务
                    CpuDataRefreshTask memoryDataRefreshTask = new CpuDataRefreshTask(monitorBatchConditionCopy, sessionId);
                    //5秒后重新执行
                    ScheduledTaskManagement.addJob(memoryDataRefreshTask, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5));
                }
            }
        });

    }
}
