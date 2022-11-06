package com.dting.show.server.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dting.show.server.entity.ThreadPoolConfig;
import com.dting.show.server.mapper.ThreadPoolConfigMapper;
import com.dting.show.server.service.ThreadPoolConfigService;
import org.springframework.stereotype.Service;


/**
 * *************************************************<br/>
 * 线程池配置业务服务类<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/6 16:36
 */
@Service
public class ThreadPoolConfigServiceImpl implements ThreadPoolConfigService {

    private final ThreadPoolConfigMapper threadPoolConfigMapper;

    public ThreadPoolConfigServiceImpl(ThreadPoolConfigMapper threadPoolConfigMapper) {
        this.threadPoolConfigMapper = threadPoolConfigMapper;
    }


    @Override
    public ThreadPoolConfig findThreadPoolConfigByThreadPoolGroupName(String threadPoolGroupName) {
        if(StrUtil.isBlank(threadPoolGroupName)) {
            throw new IllegalArgumentException("threadPoolGroupName 不允许为空！");
        }
        QueryWrapper<ThreadPoolConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("thread_pool_group_name", threadPoolGroupName);
        return threadPoolConfigMapper.selectOne(queryWrapper);
    }
    @Override
    public void save(ThreadPoolConfig entity) {
        threadPoolConfigMapper.insert(entity);
    }
}
