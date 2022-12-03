package com.dting.show.server.service;

import com.dting.show.server.conditions.EnvCondition;
import com.dting.show.server.entity.DtingEnv;

import java.util.List;

/**
 * *************************************************<br/>
 * 环境服务<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:30
 */
public interface DtingEnvService {

    /**
     * 查询 根据环境名
     *
     * @param envName 环境名称
     * @return 环境信息
     */
    DtingEnv findByName(String envName);

    /**
     * 环境信息保存
     *
     * @param dtingEnv 环境信息
     */
    void save(DtingEnv dtingEnv);

    List<DtingEnv> findByEnvCondition(EnvCondition envCondition);
}
