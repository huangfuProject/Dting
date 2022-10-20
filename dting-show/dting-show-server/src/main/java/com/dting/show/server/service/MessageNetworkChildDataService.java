package com.dting.show.server.service;

import com.dting.show.server.entity.MessageNetworkChildData;

import java.util.List;

/**
 * 网络子数据服务接口
 *
 * @author huangfu
 * @date 2022年10月20日13:58:24
 */
public interface MessageNetworkChildDataService {

    /**
     * 批量保存
     *
     * @param messageNetworkChildDataList 网卡详细读写数据
     */
    void batchSave(List<MessageNetworkChildData> messageNetworkChildDataList);

}
