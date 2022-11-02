package com.dting.show.server.websocket;


import com.dting.show.server.constant.RedisKeyUtil;
import com.dting.show.server.utils.SpringUtil;
import com.dting.show.server.websocket.tasks.CpuRefreshTask;
import com.dting.show.server.websocket.tasks.MemoryRefreshTask;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动态的迭代任务池子，主要用于迭代查询需要推送的websocket信息
 *
 * @author huangfu
 * @date 2022年10月26日16:54:10
 */
@Component
public class DynamicIterativeTaskPoolService implements InitializingBean, DisposableBean {


    private final static AtomicBoolean IS_RUN = new AtomicBoolean(true);

    /**
     * 连接池
     */
    private final static Map<String, MessageWebSocket> SOCKET_POOL = new ConcurrentHashMap<>(32);

    /**
     * 核心线程数
     */
    private final static int CORE_SIZE = Math.max(Runtime.getRuntime().availableProcessors() / 4, 2);

    /**
     * 内存的线程的id
     */
    private final static AtomicInteger MEMORY_THREAD_ID = new AtomicInteger(0);

    /**
     * CPU的线程的id
     */
    private final static AtomicInteger CPU_THREAD_ID = new AtomicInteger(0);

    /**
     * 线程池
     */
    private final static ScheduledThreadPoolExecutor DYNAMIC_THREAD = new ScheduledThreadPoolExecutor(1, r -> {
        Thread thread = new Thread(r);
        thread.setName("DYNAMIC_THREAD");
        return thread;
    }, new ThreadPoolExecutor.AbortPolicy());


    /**
     * 线程池 内存动态更新线程池
     */
    private final static ThreadPoolExecutor DYNAMIC_THREAD_MEMORY = new ThreadPoolExecutor(CORE_SIZE, CORE_SIZE * 2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024), r -> {
        Thread thread = new Thread(r);
        thread.setName(String.format("DYNAMIC_THREAD_MEMORY:%s", MEMORY_THREAD_ID.incrementAndGet()));
        return thread;
    }, new ThreadPoolExecutor.CallerRunsPolicy());


    /**
     * 线程池 CPU动态更新线程池
     */
    private final static ThreadPoolExecutor DYNAMIC_THREAD_CPU = new ThreadPoolExecutor(CORE_SIZE, CORE_SIZE * 2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024), r -> {
        Thread thread = new Thread(r);
        thread.setName(String.format("DYNAMIC_THREAD_CPU:%s", CPU_THREAD_ID.incrementAndGet()));
        return thread;
    }, new ThreadPoolExecutor.CallerRunsPolicy());


    /**
     * 添加一个链接
     *
     * @param messageWebSocket 消息对象
     */
    public static void addConnection(MessageWebSocket messageWebSocket) {
        // 缓存消息数据
        SOCKET_POOL.put(messageWebSocket.getSessionId(), messageWebSocket);
    }

    /**
     * 删除数据
     *
     * @param sessionId 会话id
     */
    public static void removeConnection(String sessionId) {
        StringRedisTemplate redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        SOCKET_POOL.remove(sessionId);
        //删除活跃的管道
        redisTemplate.delete(RedisKeyUtil.sessionActiveKeyFormat(sessionId));
        //删除对于内存的监听器
        redisTemplate.delete(RedisKeyUtil.dtingMemoryCacheFormat(sessionId));
    }


    @Override
    public void destroy() throws Exception {
        IS_RUN.compareAndSet(true, false);
        DYNAMIC_THREAD.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DYNAMIC_THREAD.scheduleWithFixedDelay(() -> {
            if (IS_RUN.get()) {
                SOCKET_POOL.forEach((sessionId, messageWebSocket) -> {
                    //内存数据监听
                    DYNAMIC_THREAD_MEMORY.execute(new MemoryRefreshTask(sessionId, messageWebSocket));
                    //CPU数据监听
                    DYNAMIC_THREAD_CPU.execute(new CpuRefreshTask(sessionId, messageWebSocket));
                });
            }
        }, 1, 3, TimeUnit.SECONDS);

    }
}
