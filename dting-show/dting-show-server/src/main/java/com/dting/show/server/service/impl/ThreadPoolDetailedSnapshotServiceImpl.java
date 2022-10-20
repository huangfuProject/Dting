package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.show.server.entity.ThreadPoolDetailedSnapshot;
import com.dting.show.server.mapper.ThreadPoolDetailedSnapshotMapper;
import com.dting.show.server.service.ThreadPoolDetailedSnapshotService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 线程池详细快照
 *
 * @author huangfu
 * @date 2022年10月20日16:05:16
 */
@Service
public class ThreadPoolDetailedSnapshotServiceImpl implements ThreadPoolDetailedSnapshotService {

    private final ThreadPoolDetailedSnapshotMapper threadPoolDetailedSnapshotMapper;

    public ThreadPoolDetailedSnapshotServiceImpl(ThreadPoolDetailedSnapshotMapper threadPoolDetailedSnapshotMapper) {
        this.threadPoolDetailedSnapshotMapper = threadPoolDetailedSnapshotMapper;
    }

    @Override
    public void batchSave(List<ThreadPoolDetailedSnapshot> threadPoolDetailedSnapshots) {
        if(CollectionUtil.isNotEmpty(threadPoolDetailedSnapshots)) {
            threadPoolDetailedSnapshotMapper.batchInsert(threadPoolDetailedSnapshots);
        }
    }
}
