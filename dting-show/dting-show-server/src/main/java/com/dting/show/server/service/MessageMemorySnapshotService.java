package com.dting.show.server.service;

import com.dting.show.server.conditions.MemoryBatchCondition;
import com.dting.show.server.entity.MessageMemorySnapshot;

import java.util.List;

/**
 * 内存使用情况业务接口
 *
 * @author huangfu
 * @date 2022年10月20日08:19:19
 */
public interface MessageMemorySnapshotService {

    /**
     * 内存信息批量查询 基于条件
     *
     * @param memoryBatchCondition 内存批量查询条件
     * @return 内存快照的详情
     */
    List<MessageMemorySnapshot> memoryBatchFindByCondition(MemoryBatchCondition memoryBatchCondition);

    /**
     * 批量插入
     *
     * @param messageMemorySnapshotList 内存数据
     */
    void batchSave(List<MessageMemorySnapshot> messageMemorySnapshotList);
}
