package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.show.server.entity.MessageNetworkChildData;
import com.dting.show.server.mapper.MessageNetworkChildDataMapper;
import com.dting.show.server.service.MessageNetworkChildDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网络子数据服务接口
 *
 * @author huangfu
 * @date 2022年10月20日13:59:25
 */
@Service
public class MessageNetworkChildDataServiceImpl implements MessageNetworkChildDataService {

    private final MessageNetworkChildDataMapper messageNetworkChildDataMapper;

    public MessageNetworkChildDataServiceImpl(MessageNetworkChildDataMapper messageNetworkChildDataMapper) {
        this.messageNetworkChildDataMapper = messageNetworkChildDataMapper;
    }

    @Override
    public void batchSave(List<MessageNetworkChildData> messageNetworkChildDataList) {
        if (CollectionUtil.isNotEmpty(messageNetworkChildDataList)) {
            messageNetworkChildDataMapper.batchInsert(messageNetworkChildDataList);
        }

    }
}
