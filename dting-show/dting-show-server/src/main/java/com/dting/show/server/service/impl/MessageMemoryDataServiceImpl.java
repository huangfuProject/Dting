package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.show.server.entity.MessageMemoryData;
import com.dting.show.server.mapper.MessageMemoryDataMapper;
import com.dting.show.server.service.MessageMemoryDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内存业务实现类
 *
 * @author huangfu
 * @date 2022年10月20日08:21:11
 */
@Service
public class MessageMemoryDataServiceImpl implements MessageMemoryDataService {

    private final MessageMemoryDataMapper messageMemoryDataMapper;

    public MessageMemoryDataServiceImpl(MessageMemoryDataMapper messageMemoryDataMapper) {
        this.messageMemoryDataMapper = messageMemoryDataMapper;
    }

    @Override
    public void ignoreOnlyBatchSave(List<MessageMemoryData> messageMemoryDataList) {
        if(CollectionUtil.isNotEmpty(messageMemoryDataList)) {
            messageMemoryDataMapper.ignoreOnlyBatchInsert(messageMemoryDataList);
        }
    }
}
