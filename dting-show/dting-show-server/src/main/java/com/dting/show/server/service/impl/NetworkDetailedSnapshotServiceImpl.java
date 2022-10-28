package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.show.server.entity.NetworkDetailedSnapshot;
import com.dting.show.server.mapper.NetworkDetailedSnapshotMapper;
import com.dting.show.server.service.NetworkDetailedSnapshotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 网络子数据服务接口
 *
 * @author huangfu
 * @date 2022年10月20日13:59:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NetworkDetailedSnapshotServiceImpl implements NetworkDetailedSnapshotService {

    private final NetworkDetailedSnapshotMapper networkDetailedSnapshotMapper;

    public NetworkDetailedSnapshotServiceImpl(NetworkDetailedSnapshotMapper networkDetailedSnapshotMapper) {
        this.networkDetailedSnapshotMapper = networkDetailedSnapshotMapper;
    }

    @Override
    public void batchSave(List<NetworkDetailedSnapshot> networkDetailedSnapshotList) {
        if (CollectionUtil.isNotEmpty(networkDetailedSnapshotList)) {
            networkDetailedSnapshotMapper.batchInsert(networkDetailedSnapshotList);
        }

    }
}
