package com.dting.show.server.service.impl;

import com.dting.show.server.entity.MessageTaskRunLogData;
import com.dting.show.server.mapper.MessageTaskRunLogDataMapper;
import com.dting.show.server.service.MessageTaskRunLogDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务执行日志的业务实现
 *
 * @author huangfu
 * @date 2022年10月20日11:46:59
 */
@Service
public class MessageTaskRunLogDataServiceImpl implements MessageTaskRunLogDataService {

    private final MessageTaskRunLogDataMapper messageTaskRunLogDataMapper;

    public MessageTaskRunLogDataServiceImpl(MessageTaskRunLogDataMapper messageTaskRunLogDataMapper) {
        this.messageTaskRunLogDataMapper = messageTaskRunLogDataMapper;
    }

    @Override
    public void ignoreOnlyBatchSave(List<MessageTaskRunLogData> messageTaskRunLogDataList) {
        messageTaskRunLogDataMapper.ignoreOnlyBatchInsert(messageTaskRunLogDataList);
    }
}
