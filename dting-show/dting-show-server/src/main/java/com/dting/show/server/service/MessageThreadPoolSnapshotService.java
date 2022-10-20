package com.dting.show.server.service;

import com.dting.show.server.entity.MessageThreadPoolSnapshot;

import java.util.List;

/**
 * 线程池采集快照记录
 *
 * @author huangfu
 * @date 2022年10月20日16:24:49
 */
public interface MessageThreadPoolSnapshotService {
    /**
     * 忽略唯一性检查的保存，当出现唯一索引冲突的时候，不保存
     *
     * @param messageThreadPoolSnapshotList 网卡数据
     */
    void ignoreOnlyBatchSave(List<MessageThreadPoolSnapshot> messageThreadPoolSnapshotList);
}
