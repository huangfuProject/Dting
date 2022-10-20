package com.dting.show.server.service;

import com.dting.show.server.entity.MessageTaskRunLogData;

import java.util.List;

/**
 * 任务执行日志的业务服务类
 *
 * @author huangfu
 * @date 2022年10月20日11:45:51
 */
public interface MessageTaskRunLogDataService {
    /**
     * 忽略唯一性检查的保存，当出现唯一索引冲突的时候，不保存
     *
     * @param messageTaskRunLogDataList 任务数据
     */
    void ignoreOnlyBatchSave(List<MessageTaskRunLogData> messageTaskRunLogDataList);
}
