package com.dting.show.server.service;

import com.dting.show.server.entity.MessageNetworkData;

import java.util.List;

/**
 * 网卡业务服务
 *
 * @author huangfu
 * @date 2022年10月20日11:11:42
 */
public interface MessageNetworkDataService {

    /**
     * 忽略唯一性检查的保存，当出现唯一索引冲突的时候，不保存
     *
     * @param messageNetworkDataList 网卡数据
     */
    void ignoreOnlyBatchSave(List<MessageNetworkData> messageNetworkDataList);
}
