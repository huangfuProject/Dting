package com.dting.show.server.service;

import com.dting.show.server.entity.MessageMemoryData;

import java.util.List;

/**
 * 内存使用情况业务接口
 *
 * @author huangfu
 * @date 2022年10月20日08:19:19
 */
public interface MessageMemoryDataService {

    /**
     * 忽略唯一性检查的保存，当出现唯一索引冲突的时候，不保存
     *
     * @param messageMemoryDataList 内存数据
     */
    void ignoreOnlyBatchSave(List<MessageMemoryData> messageMemoryDataList);
}
