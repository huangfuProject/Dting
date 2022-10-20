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
     * 忽略唯一性检查的保存，当出现唯一索引冲突的时候，不保存
     *
     * @param messageCpuSnapshotList Cpu数据列表
     */
    void ignoreOnlyBatchSave(List<MessageCpuSnapshot> messageCpuSnapshotList);
}
