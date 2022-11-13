package com.dting.show.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dting.show.server.dto.InstanceData;
import com.dting.show.server.entity.DtingEnv;
import com.dting.show.server.entity.DtingInstance;
import com.dting.show.server.mapper.DtingInstanceMapper;
import com.dting.show.server.service.DtingEnvService;
import com.dting.show.server.service.DtingInstanceService;
import com.dting.show.server.service.DtingServerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *************************************************<br/>
 * 实例实现<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:32
 */
@Service
public class DtingInstanceServiceImpl implements DtingInstanceService {

    /**
     * 实例 dom
     */
    private final DtingInstanceMapper dtingInstanceMapper;
    /**
     * 环境服务
     */
    private final DtingEnvService dtingEnvService;

    /**
     * 服务信息
     */
    private final DtingServerService dtingServerService;

    public DtingInstanceServiceImpl(DtingInstanceMapper dtingInstanceMapper, DtingEnvService dtingEnvService, DtingServerService dtingServerService) {
        this.dtingInstanceMapper = dtingInstanceMapper;
        this.dtingEnvService = dtingEnvService;
        this.dtingServerService = dtingServerService;
    }

    @Override
    public List<DtingInstance> findByServerId(Integer serverId) {
        QueryWrapper<DtingInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("server_id", serverId);
        return dtingInstanceMapper.selectList(queryWrapper);
    }

    @Override
    public DtingInstance findByServerIdAndInstanceName(Integer serverId, String instanceName) {
        QueryWrapper<DtingInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("server_id", serverId);
        queryWrapper.eq("instance_name", instanceName);
        return dtingInstanceMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DtingInstance> findByTimeout() {
        QueryWrapper<DtingInstance> queryWrapper = new QueryWrapper<>();

        long thisDate = System.currentTimeMillis();
        queryWrapper.eq("state", "0");
        queryWrapper.ne("timeout", "-1");
        queryWrapper.last("and timeout <= " + thisDate + "- last_update_time");
        return dtingInstanceMapper.selectList(queryWrapper);
    }

    @Override
    public void save(DtingInstance dtingInstance) {
        dtingInstanceMapper.insert(dtingInstance);
    }

}
