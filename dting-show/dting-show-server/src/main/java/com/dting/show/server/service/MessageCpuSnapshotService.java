package com.dting.show.server.service;

import com.dting.show.server.conditions.MonitorBatchCondition;
import com.dting.show.server.entity.MessageCpuSnapshot;
import com.dting.show.server.vos.monitoring.MemoryDataVo;
import com.dting.show.server.vos.monitoring.SystemCpuDataVo;

import java.util.List;

/**
 * Cpu使用操作的业务类
 *
 * @author huangfu
 * @date 2022年10月19日14:41:17
 */
public interface MessageCpuSnapshotService {

    /**
     * cpu数据监听
     *
     * @param monitorBatchCondition 监听条件
     * @param enablePlan            启动计划
     * @return 返回一个监听数据，并开启监听任务
     */
    SystemCpuDataVo cpuMonitoring(MonitorBatchCondition monitorBatchCondition, boolean enablePlan);


    /**
     * cpu数据批量查询 转换为vo格式
     *
     * @param monitorBatchCondition 内存数据查询条件
     * @return vo格式的数据
     */
    SystemCpuDataVo cpuQueryByCondition(MonitorBatchCondition monitorBatchCondition);


    /**
     * CPU数据 批量查询
     *
     * @param monitorBatchCondition 批量查询条件
     * @return 返回查询结果
     */
    List<MessageCpuSnapshot> cpuBatchFindByCondition(MonitorBatchCondition monitorBatchCondition);

    /**
     * 批量插入数据
     *
     * @param messageCpuSnapshotList Cpu数据列表
     */
    void batchSave(List<MessageCpuSnapshot> messageCpuSnapshotList);
}
