package com.dting.show.server.service;

import com.dting.show.server.conditions.InstanceCondition;
import com.dting.show.server.entity.DtingInstance;
import com.dting.show.server.entity.DtingServer;

import java.util.List;

/**
 * *************************************************<br/>
 * 实例服务<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:31
 */
public interface DtingInstanceService {

    /**
     * 根据环境id查询
     *
     * @param serverId 服务id
     * @return 返回所有的服务信息
     */
    List<DtingInstance> findByServerId(Integer serverId);

    /**
     * 根据服务id和实例名称查询
     *
     * @param serverId     服务id
     * @param instanceName 服务名称
     * @return 服务信息
     */
    DtingInstance findByServerIdAndInstanceName(Integer serverId, String instanceName);


    /**
     * 查询超时的实例信息
     *
     * @return 所有超时的实例
     */
    List<DtingInstance> findByTimeout();

    /**
     * 保存实例
     *
     * @param dtingInstance 实例
     */
    void save(DtingInstance dtingInstance);

    /**
     * 根据实例条件查询实例
     *
     * @param instanceCondition 实例查询条件
     * @return 实例信息
     */
    List<DtingInstance> findByInstanceCondition(InstanceCondition instanceCondition);
}


