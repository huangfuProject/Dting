package com.dting.show.server.service;

import com.dting.show.server.entity.MessageCpuSnapshot;

import java.util.List;

/**
 * Cpu使用操作的业务类
 *
 * @author huangfu
 * @date 2022年10月19日14:41:17
 */
public interface MessageCpuSnapshotService {

    /**
     * 批量插入数据
     *
     * @param messageCpuSnapshotList Cpu数据列表
     */
    void batchSave(List<MessageCpuSnapshot> messageCpuSnapshotList);
}
