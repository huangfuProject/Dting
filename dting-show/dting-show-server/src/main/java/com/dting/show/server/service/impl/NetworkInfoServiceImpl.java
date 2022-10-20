package com.dting.show.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.show.server.entity.NetworkInfo;
import com.dting.show.server.mapper.NetworkInfoMapper;
import com.dting.show.server.service.NetworkInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网络子数据服务接口
 *
 * @author huangfu
 * @date 2022年10月20日13:59:25
 */
@Service
public class NetworkInfoServiceImpl implements NetworkInfoService {

    private final NetworkInfoMapper networkInfoMapper;

    public NetworkInfoServiceImpl(NetworkInfoMapper networkInfoMapper) {
        this.networkInfoMapper = networkInfoMapper;
    }

    @Override
    public void batchSave(List<NetworkInfo> networkInfoList) {
        if (CollectionUtil.isNotEmpty(networkInfoList)) {
            networkInfoMapper.batchInsert(networkInfoList);
        }

    }
}
