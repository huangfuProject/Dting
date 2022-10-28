package com.dting.show.server.service.impl;

import com.dting.show.server.entity.MessageTaskRunSnapshot;
import com.dting.show.server.mapper.MessageTaskRunSnapshotMapper;
import com.dting.show.server.service.MessageTaskRunSnapshotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务执行日志的业务实现
 *
 * @author huangfu
 * @date 2022年10月20日11:46:59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MessageTaskRunSnapshotServiceImpl implements MessageTaskRunSnapshotService {

    private final MessageTaskRunSnapshotMapper messageTaskRunSnapshotMapper;

    public MessageTaskRunSnapshotServiceImpl(MessageTaskRunSnapshotMapper messageTaskRunSnapshotMapper) {
        this.messageTaskRunSnapshotMapper = messageTaskRunSnapshotMapper;
    }

    @Override
    public void batchSave(List<MessageTaskRunSnapshot> messageTaskRunSnapshotList) {
        messageTaskRunSnapshotMapper.batchInsert(messageTaskRunSnapshotList);
    }
}
