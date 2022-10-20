package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.show.server.entity.MessageThreadPoolSnapshot;
import com.dting.show.server.mapper.MessageThreadPoolSnapshotMapper;
import com.dting.show.server.service.MessageThreadPoolSnapshotService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 线程池采集快照
 *
 * @author huangfu
 * @date 2022年10月20日16:25:49
 */
@Service
public class MessageThreadPoolSnapshotServiceImpl implements MessageThreadPoolSnapshotService {

    private final MessageThreadPoolSnapshotMapper messageThreadPoolSnapshotMapper;

    public MessageThreadPoolSnapshotServiceImpl(MessageThreadPoolSnapshotMapper messageThreadPoolSnapshotMapper) {
        this.messageThreadPoolSnapshotMapper = messageThreadPoolSnapshotMapper;
    }

    @Override
    public void ignoreOnlyBatchSave(List<MessageThreadPoolSnapshot> messageThreadPoolSnapshotList) {
        if (CollectionUtil.isNotEmpty(messageThreadPoolSnapshotList)) {
            messageThreadPoolSnapshotMapper.ignoreOnlyBatchInsert(messageThreadPoolSnapshotList);
        }

    }
}
