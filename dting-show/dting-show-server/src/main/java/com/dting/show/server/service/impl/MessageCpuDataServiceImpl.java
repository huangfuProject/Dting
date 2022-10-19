package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.show.server.entity.MessageCpuData;
import com.dting.show.server.mapper.MessageCpuDataMapper;
import com.dting.show.server.service.MessageCpuDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CPU业务操作类
 *
 * @author huangfu
 * @date 2022年10月19日14:42:55
 */
@Service
public class MessageCpuDataServiceImpl implements MessageCpuDataService {

    private final MessageCpuDataMapper messageCpuDataMapper;

    public MessageCpuDataServiceImpl(MessageCpuDataMapper messageCpuDataMapper) {
        this.messageCpuDataMapper = messageCpuDataMapper;
    }

    @Override
    public void ignoreOnlyBatchSave(List<MessageCpuData> messageCpuDataList) {
        if(CollectionUtil.isNotEmpty(messageCpuDataList)) {
            this.messageCpuDataMapper.ignoreOnlyBatchInsert(messageCpuDataList);
        }

    }
}
