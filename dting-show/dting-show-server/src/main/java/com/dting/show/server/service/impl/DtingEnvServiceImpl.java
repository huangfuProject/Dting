package com.dting.show.server.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dting.show.server.conditions.EnvCondition;
import com.dting.show.server.entity.DtingEnv;
import com.dting.show.server.mapper.DtingEnvMapper;
import com.dting.show.server.service.DtingEnvService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *************************************************<br/>
 * 环境服务实现<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:31
 */
@Service
public class DtingEnvServiceImpl implements DtingEnvService {

    /**
     * 环境dom
     */
    private final DtingEnvMapper dtingEnvMapper;

    public DtingEnvServiceImpl(DtingEnvMapper dtingEnvMapper) {
        this.dtingEnvMapper = dtingEnvMapper;
    }

    @Override
    public DtingEnv findByName(String envName) {
        QueryWrapper<DtingEnv> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("env_name", envName);
        return dtingEnvMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(DtingEnv dtingEnv) {
        dtingEnvMapper.insert(dtingEnv);
    }

    @Override
    public List<DtingEnv> findByEnvCondition(EnvCondition envCondition) {
        QueryWrapper<DtingEnv> queryWrapper = new QueryWrapper<>();
        String envRegularName = envCondition.getEnvRegularName();
        Long startTime = envCondition.getStartTime();
        Long endTime = envCondition.getEndTime();

        if (startTime != null && endTime != null) {
            queryWrapper.between(endTime >= startTime, "create_date", startTime, endTime);
        }
        queryWrapper.apply(StrUtil.isNotBlank(envRegularName), String.format("env_name REGEXP '%s'", envRegularName));
        return dtingEnvMapper.selectList(queryWrapper);
    }
}
