package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.show.server.entity.MessageNetworkData;
import com.dting.show.server.mapper.MessageNetworkDataMapper;
import com.dting.show.server.service.MessageNetworkDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网卡数据的业务服务
 *
 * @author huangfu
 * @date 2022年10月20日11:15:26
 */
@Service
public class MessageNetworkDataServiceImpl implements MessageNetworkDataService {

    private final MessageNetworkDataMapper messageNetworkDataMapper;

    public MessageNetworkDataServiceImpl(MessageNetworkDataMapper messageNetworkDataMapper) {
        this.messageNetworkDataMapper = messageNetworkDataMapper;
    }

    @Override
    public void ignoreOnlyBatchSave(List<MessageNetworkData> messageNetworkDataList) {
        if(CollectionUtil.isNotEmpty(messageNetworkDataList)) {
            messageNetworkDataMapper.ignoreOnlyBatchInsert(messageNetworkDataList);
        }

    }
}
