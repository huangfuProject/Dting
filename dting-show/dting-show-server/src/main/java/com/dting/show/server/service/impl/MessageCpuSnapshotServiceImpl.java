package com.dting.show.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dting.show.server.conditions.MonitorBatchCondition;
import com.dting.show.server.constant.RedisKeyUtil;
import com.dting.show.server.entity.MessageCpuSnapshot;
import com.dting.show.server.mapper.MessageCpuSnapshotMapper;
import com.dting.show.server.service.MessageCpuSnapshotService;
import com.dting.show.server.tasks.CpuDataRefreshTask;
import com.dting.show.server.tasks.MemoryDataRefreshTask;
import com.dting.show.server.utils.ScheduledTaskManagement;
import com.dting.show.server.vos.data.SystemCpuData;
import com.dting.show.server.vos.monitoring.SystemCpuDataVo;
import org.springframework.aop.framework.AopContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * CPU业务操作类
 *
 * @author huangfu
 * @date 2022年10月19日14:42:55
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MessageCpuSnapshotServiceImpl implements MessageCpuSnapshotService {

    private final MessageCpuSnapshotMapper messageCpuSnapshotMapper;

    private final StringRedisTemplate redisTemplate;

    public MessageCpuSnapshotServiceImpl(MessageCpuSnapshotMapper messageCpuSnapshotMapper, StringRedisTemplate redisTemplate) {
        this.messageCpuSnapshotMapper = messageCpuSnapshotMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public SystemCpuDataVo cpuMonitoring(MonitorBatchCondition monitorBatchCondition, boolean enablePlan) {
        long endTime = monitorBatchCondition.getEndTime();
        if (endTime < 0) {
            monitorBatchCondition.setEndTime(System.currentTimeMillis());
        }
        SystemCpuDataVo systemCpuDataVo = ((MessageCpuSnapshotServiceImpl) AopContext.currentProxy()).cpuQueryByCondition(monitorBatchCondition);
        String monitorId = systemCpuDataVo.getMonitorId();
        if (enablePlan) {
            //判断redis
            String sessionActiveKey = RedisKeyUtil.sessionActiveKeyFormat(monitorId);
            //开启数据 并设置过期时间
            redisTemplate.opsForValue().set(sessionActiveKey, "1", 120, TimeUnit.SECONDS);
            //将结束时间设置为开始时间
            monitorBatchCondition.setStartTime(monitorBatchCondition.getEndTime());
            //生成任务
            CpuDataRefreshTask cpuDataRefreshTask = new CpuDataRefreshTask(monitorBatchCondition, monitorId);
            //5秒后重新执行
            ScheduledTaskManagement.addJob(cpuDataRefreshTask, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5));
        }
        return systemCpuDataVo;
    }

    @Override
    public SystemCpuDataVo cpuQueryByCondition(MonitorBatchCondition monitorBatchCondition) {
        //查询数据
        List<MessageCpuSnapshot> messageMemorySnapshots = ((MessageCpuSnapshotService) AopContext.currentProxy()).cpuBatchFindByCondition(monitorBatchCondition);
        //数据转换
        SystemCpuDataVo systemCpuDataVo = new SystemCpuDataVo();
        List<SystemCpuData> cpuDataList = messageMemorySnapshots.stream().map(messageMemorySnapshot -> {
            SystemCpuData systemCpuData = new SystemCpuData();
            BeanUtil.copyProperties(messageMemorySnapshot, systemCpuData);
            systemCpuData.setDateValue(messageMemorySnapshot.getCollectTime());
            return systemCpuData;
        }).collect(Collectors.toList());
        systemCpuDataVo.setSystemCpuDataList(cpuDataList);
        String monitorId = IdUtil.fastSimpleUUID();
        systemCpuDataVo.setMonitorId(monitorId);
        return systemCpuDataVo;
    }

    @Override
    public List<MessageCpuSnapshot> cpuBatchFindByCondition(MonitorBatchCondition monitorBatchCondition) {
        QueryWrapper<MessageCpuSnapshot> queryWrapper = new QueryWrapper<>();

        String serverEnv = monitorBatchCondition.getServerEnv();
        String serverKey = monitorBatchCondition.getServerKey();
        String instanceKey = monitorBatchCondition.getInstanceKey();


        if (StrUtil.isBlank(serverEnv) || StrUtil.isBlank(serverKey) || StrUtil.isBlank(instanceKey)) {
            return new ArrayList<>();
        }

        Long startTime = monitorBatchCondition.getStartTime();
        Long endTime = monitorBatchCondition.getEndTime();

        // 消息标签
        queryWrapper.eq("server_env", serverEnv);
        queryWrapper.eq("server_key", serverKey);
        queryWrapper.eq("instance_key", instanceKey);

        if (startTime != null && startTime > 0) {
            queryWrapper.gt("collect_time", startTime);
        }

        if (endTime != null && endTime > 0) {
            queryWrapper.le("collect_time", endTime);
        }
        //根据时间 正序排列
        queryWrapper.orderByAsc("collect_time");
        return messageCpuSnapshotMapper.selectList(queryWrapper);
    }

    @Override
    public void batchSave(List<MessageCpuSnapshot> messageCpuSnapshotList) {
        if (CollectionUtil.isNotEmpty(messageCpuSnapshotList)) {
            this.messageCpuSnapshotMapper.batchInsert(messageCpuSnapshotList);
        }

    }
}
