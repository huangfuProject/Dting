package com.dting.show.server.service.impl;

import com.dting.show.server.entity.MessageTaskRunSnapshot;
import com.dting.show.server.mapper.MessageTaskRunSnapshotMapper;
import com.dting.show.server.service.MessageTaskRunSnapshotService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务执行日志的业务实现
 *
 * @author huangfu
 * @date 2022年10月20日11:46:59
 */
@Service
public class MessageTaskRunSnapshotServiceImpl implements MessageTaskRunSnapshotService {

    private final MessageTaskRunSnapshotMapper messageTaskRunSnapshotMapper;

    public MessageTaskRunSnapshotServiceImpl(MessageTaskRunSnapshotMapper messageTaskRunSnapshotMapper) {
        this.messageTaskRunSnapshotMapper = messageTaskRunSnapshotMapper;
    }

    @Override
    public void ignoreOnlyBatchSave(List<MessageTaskRunSnapshot> messageTaskRunSnapshotList) {
        messageTaskRunSnapshotMapper.ignoreOnlyBatchInsert(messageTaskRunSnapshotList);
    }
}
