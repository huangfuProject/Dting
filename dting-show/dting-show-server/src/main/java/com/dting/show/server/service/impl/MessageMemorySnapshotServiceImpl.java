package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.show.server.entity.MessageMemorySnapshot;
import com.dting.show.server.mapper.MessageMemorySnapshotMapper;
import com.dting.show.server.service.MessageMemorySnapshotService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内存业务实现类
 *
 * @author huangfu
 * @date 2022年10月20日08:21:11
 */
@Service
public class MessageMemorySnapshotServiceImpl implements MessageMemorySnapshotService {

    private final MessageMemorySnapshotMapper messageMemorySnapshotMapper;

    public MessageMemorySnapshotServiceImpl(MessageMemorySnapshotMapper messageMemorySnapshotMapper) {
        this.messageMemorySnapshotMapper = messageMemorySnapshotMapper;
    }

    @Override
    public void ignoreOnlyBatchSave(List<MessageMemorySnapshot> messageMemorySnapshotList) {
        if(CollectionUtil.isNotEmpty(messageMemorySnapshotList)) {
            messageMemorySnapshotMapper.ignoreOnlyBatchInsert(messageMemorySnapshotList);
        }
    }
}
