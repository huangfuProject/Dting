package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dting.show.server.conditions.MemoryBatchCondition;
import com.dting.show.server.entity.MessageMemorySnapshot;
import com.dting.show.server.mapper.MessageMemorySnapshotMapper;
import com.dting.show.server.service.MessageMemorySnapshotService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存业务实现类
 *
 * @author huangfu
 * @date 2022年10月20日08:21:11
 */
@Service
public class MessageMemorySnapshotServiceImpl extends ServiceImpl<MessageMemorySnapshotMapper, MessageMemorySnapshot> implements MessageMemorySnapshotService {

    private final MessageMemorySnapshotMapper messageMemorySnapshotMapper;

    public MessageMemorySnapshotServiceImpl(MessageMemorySnapshotMapper messageMemorySnapshotMapper) {
        this.messageMemorySnapshotMapper = messageMemorySnapshotMapper;
    }

    @Override
    public List<MessageMemorySnapshot> memoryBatchFindByCondition(MemoryBatchCondition memoryBatchCondition) {
        QueryWrapper<MessageMemorySnapshot> queryWrapper = new QueryWrapper<>();
        String messageTag = memoryBatchCondition.getMessageTag();
        String address = memoryBatchCondition.getAddress();
        if (StrUtil.isBlank(messageTag) || StrUtil.isBlank(address)) {
            return new ArrayList<>();
        }

        Integer id = memoryBatchCondition.getId();
        Long startTime = memoryBatchCondition.getStartTime();
        Long endTime = memoryBatchCondition.getEndTime();

        // 消息标签
        queryWrapper.eq("message_tag", messageTag);
        queryWrapper.eq("message_ip", address);

        if (id != null) {
            queryWrapper.ge("id", id);
        }
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
    public void ignoreOnlyBatchSave(List<MessageMemorySnapshot> messageMemorySnapshotList) {
        if (CollectionUtil.isNotEmpty(messageMemorySnapshotList)) {
            messageMemorySnapshotMapper.ignoreOnlyBatchInsert(messageMemorySnapshotList);
        }
    }
}
