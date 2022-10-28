package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dting.show.server.conditions.MemoryBatchCondition;
import com.dting.show.server.entity.MessageMemorySnapshot;
import com.dting.show.server.mapper.MessageMemorySnapshotMapper;
import com.dting.show.server.service.MessageMemorySnapshotService;
import com.dting.show.server.vos.monitoring.JvmMemoryData;
import com.dting.show.server.vos.monitoring.MemoryDataVo;
import com.dting.show.server.vos.monitoring.SystemMemoryData;
import com.dting.show.server.vos.monitoring.SystemSwapData;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存业务实现类
 *
 * @author huangfu
 * @date 2022年10月20日08:21:11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MessageMemorySnapshotServiceImpl implements MessageMemorySnapshotService {

    private final MessageMemorySnapshotMapper messageMemorySnapshotMapper;

    public MessageMemorySnapshotServiceImpl(MessageMemorySnapshotMapper messageMemorySnapshotMapper) {
        this.messageMemorySnapshotMapper = messageMemorySnapshotMapper;
    }

    @Override
    public MemoryDataVo memoryQueryByCondition(MemoryBatchCondition memoryBatchCondition) {
        //查询数据
        List<MessageMemorySnapshot> messageMemorySnapshots = ((MessageMemorySnapshotService) AopContext.currentProxy()).memoryBatchFindByCondition(memoryBatchCondition);
        //数据转换
        MemoryDataVo memoryDataVo = new MemoryDataVo();
        messageMemorySnapshots.forEach(messageMemorySnapshot -> {
            //系统内存数据
            SystemMemoryData systemMemoryData = new SystemMemoryData();
            //jvm内存数据
            JvmMemoryData jvmMemoryData = new JvmMemoryData();
            //交换区数据
            SystemSwapData systemSwapData = new SystemSwapData();

            //系统内存数据
            systemMemoryData.setMaxSystemMemory(messageMemorySnapshot.getTotalMemory());
            systemMemoryData.setUseSystemMemory(messageMemorySnapshot.getUseMemory());
            systemMemoryData.setDateValue(messageMemorySnapshot.getCollectTime());
            //jvm内存数据
            jvmMemoryData.setMaxJvmMemory(messageMemorySnapshot.getJvmTotalMemory());
            jvmMemoryData.setUseJvmMemory(messageMemorySnapshot.getJvmUseMemory());
            jvmMemoryData.setDateValue(messageMemorySnapshot.getCollectTime());
            //交换区数据
            systemSwapData.setMaxSystemSwap(messageMemorySnapshot.getTotalSwap());
            systemSwapData.setUseSystemSwap(messageMemorySnapshot.getUseSwap());
            systemSwapData.setDateValue(messageMemorySnapshot.getCollectTime());
            //开始追加数据
            memoryDataVo.addSystemMemoryData(systemMemoryData);
            memoryDataVo.addJvmMemoryData(jvmMemoryData);
            memoryDataVo.addSystemSwapData(systemSwapData);

        });
        return memoryDataVo;
    }

    @Override
    public List<MessageMemorySnapshot> memoryBatchFindByCondition(MemoryBatchCondition memoryBatchCondition) {
        QueryWrapper<MessageMemorySnapshot> queryWrapper = new QueryWrapper<>();

        String serverEnv = memoryBatchCondition.getServerEnv();
        String serverKey = memoryBatchCondition.getServerKey();
        String instanceKey = memoryBatchCondition.getInstanceKey();


        if (StrUtil.isBlank(serverEnv) || StrUtil.isBlank(serverKey) || StrUtil.isBlank(instanceKey)) {
            return new ArrayList<>();
        }

        Long startTime = memoryBatchCondition.getStartTime();
        Long endTime = memoryBatchCondition.getEndTime();

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
        return messageMemorySnapshotMapper.selectList(queryWrapper);
    }

    @Override
    public void batchSave(List<MessageMemorySnapshot> messageMemorySnapshotList) {
        if (CollectionUtil.isNotEmpty(messageMemorySnapshotList)) {
            messageMemorySnapshotMapper.batchInsert(messageMemorySnapshotList);
        }
    }
}
