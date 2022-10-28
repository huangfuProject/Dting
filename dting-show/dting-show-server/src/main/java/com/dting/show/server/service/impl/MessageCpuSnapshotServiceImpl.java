package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.show.server.entity.MessageCpuSnapshot;
import com.dting.show.server.mapper.MessageCpuSnapshotMapper;
import com.dting.show.server.service.MessageCpuSnapshotService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CPU业务操作类
 *
 * @author huangfu
 * @date 2022年10月19日14:42:55
 */
@Service
public class MessageCpuSnapshotServiceImpl implements MessageCpuSnapshotService {

    private final MessageCpuSnapshotMapper messageCpuSnapshotMapper;

    public MessageCpuSnapshotServiceImpl(MessageCpuSnapshotMapper messageCpuSnapshotMapper) {
        this.messageCpuSnapshotMapper = messageCpuSnapshotMapper;
    }

    @Override
    public void batchSave(List<MessageCpuSnapshot> messageCpuSnapshotList) {
        if(CollectionUtil.isNotEmpty(messageCpuSnapshotList)) {
            this.messageCpuSnapshotMapper.batchInsert(messageCpuSnapshotList);
        }

    }
}
