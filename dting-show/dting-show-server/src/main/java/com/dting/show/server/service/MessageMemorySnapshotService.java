package com.dting.show.server.service;

import com.dting.show.server.conditions.MonitorBatchCondition;
import com.dting.show.server.entity.MessageMemorySnapshot;
import com.dting.show.server.vos.monitoring.MemoryDataVo;

import java.util.List;

/**
 * 内存使用情况业务接口
 *
 * @author huangfu
 * @date 2022年10月20日08:19:19
 */
public interface MessageMemorySnapshotService {


    /**
     * 内存数据监听
     *
     * @param monitorBatchCondition 监听条件
     * @param enablePlan           启动计划
     * @return 返回一个监听数据，并开启监听任务
     */
    MemoryDataVo memoryMonitoring(MonitorBatchCondition monitorBatchCondition, boolean enablePlan);


    /**
     * 内存批量查询 转换为vo格式
     *
     * @param monitorBatchCondition 内存数据查询条件
     * @return vo格式的数据
     */
    MemoryDataVo memoryQueryByCondition(MonitorBatchCondition monitorBatchCondition);

    /**
     * 内存信息批量查询 基于条件
     *
     * @param monitorBatchCondition 内存批量查询条件
     * @return 内存快照的详情
     */
    List<MessageMemorySnapshot> memoryBatchFindByCondition(MonitorBatchCondition monitorBatchCondition);

    /**
     * 批量插入
     *
     * @param messageMemorySnapshotList 内存数据
     */
    void batchSave(List<MessageMemorySnapshot> messageMemorySnapshotList);

}
