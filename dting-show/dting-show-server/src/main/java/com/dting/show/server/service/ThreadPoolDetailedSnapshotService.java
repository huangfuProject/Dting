package com.dting.show.server.service;

import com.dting.show.server.entity.ThreadPoolDetailedSnapshot;

import java.util.List;

/**
 * 线程池的采集时刻的
 *
 * @author huangfu
 * @date 2022年10月20日15:27:40
 */
public interface ThreadPoolDetailedSnapshotService {

    /**
     * 批量插入
     *
     * @param threadPoolDetailedSnapshots 线程池的快照数据
     */
    void batchSave(List<ThreadPoolDetailedSnapshot> threadPoolDetailedSnapshots);
}
