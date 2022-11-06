package com.dting.show.server.service;

import com.dting.show.server.entity.ThreadPoolConfig;

/**
 * *************************************************<br/>
 * 线程池业务接口<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/6 16:39
 */
public interface ThreadPoolConfigService {

    /**
     * 查找线程池 基于线程池组
     *
     * @param threadPoolGroupName 线程池组的名称
     * @return 线程池
     */
    ThreadPoolConfig findThreadPoolConfigByThreadPoolGroupName(String threadPoolGroupName);

    /**
     * 保存一个对象
     *
     * @param threadPoolConfig 需要保存的配置
     */
    void save(ThreadPoolConfig threadPoolConfig);
}
