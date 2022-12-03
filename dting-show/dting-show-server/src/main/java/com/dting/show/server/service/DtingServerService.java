package com.dting.show.server.service;

import com.dting.show.server.conditions.ServerCondition;
import com.dting.show.server.entity.DtingServer;

import java.util.List;

/**
 * *************************************************<br/>
 * 服务 信息<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:30
 */
public interface DtingServerService {
    /**
     * 根据环境id查询
     *
     * @param envId 环境id
     * @return 返回所有的服务信息
     */
    List<DtingServer> findByEnvId(Integer envId);


    /**
     * 根据提交查询服务列表
     *
     * @param serverCondition 查询条件
     * @return 服务列表
     */
    List<DtingServer> findByServerCondition(ServerCondition serverCondition);

    /**
     * 根据环境id和服务名称查询
     *
     * @param envId      环境id
     * @param serverName 服务名称
     * @return 服务信息
     */
    DtingServer findByEnvIdAndServerName(Integer envId, String serverName);

    /**
     * 服务保存
     *
     * @param dtingServer 服务
     */
    void save(DtingServer dtingServer);

}
