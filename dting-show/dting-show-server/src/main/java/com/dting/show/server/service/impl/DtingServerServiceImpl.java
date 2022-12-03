package com.dting.show.server.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dting.show.server.conditions.ServerCondition;
import com.dting.show.server.entity.DtingServer;
import com.dting.show.server.exceptions.ServerDataException;
import com.dting.show.server.exceptions.status.ServerDataExceptionStatus;
import com.dting.show.server.mapper.DtingServerMapper;
import com.dting.show.server.service.DtingServerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *************************************************<br/>
 * 服务信息实现<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:31
 */
@Service
public class DtingServerServiceImpl implements DtingServerService {

    /**
     * 服务dom
     */
    private final DtingServerMapper dtingServerMapper;

    public DtingServerServiceImpl(DtingServerMapper dtingServerMapper) {
        this.dtingServerMapper = dtingServerMapper;
    }

    @Override
    public List<DtingServer> findByEnvId(Integer envId) {
        QueryWrapper<DtingServer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("env_id", envId);
        return dtingServerMapper.selectList(queryWrapper);
    }

    @Override
    public List<DtingServer> findByServerCondition(ServerCondition serverCondition) {
        QueryWrapper<DtingServer> queryWrapper = new QueryWrapper<>();
        Integer envId = serverCondition.getEnvId();
        if (envId == null) {
            throw new ServerDataException(ServerDataExceptionStatus.UNKNOWN_ENVIRONMENT_INFORMATION);
        }
        String serverRegularName = serverCondition.getServerRegularName();
        Long endTime = serverCondition.getEndTime();
        Long startTime = serverCondition.getStartTime();

        if (endTime != null && startTime != null) {
            queryWrapper.between(endTime >= startTime, "create_date", startTime, endTime);
        }
        queryWrapper.apply(StrUtil.isNotBlank(serverRegularName), String.format("server_name REGEXP '%s'", serverRegularName));
        return dtingServerMapper.selectList(queryWrapper);
    }

    @Override
    public DtingServer findByEnvIdAndServerName(Integer envId, String serverName) {
        QueryWrapper<DtingServer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("env_id", envId);
        queryWrapper.eq("server_name", serverName);
        return dtingServerMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(DtingServer dtingServer) {
        dtingServerMapper.insert(dtingServer);
    }
}
