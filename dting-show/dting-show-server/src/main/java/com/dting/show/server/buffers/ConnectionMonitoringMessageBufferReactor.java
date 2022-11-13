package com.dting.show.server.buffers;

import cn.hutool.core.collection.CollUtil;
import com.dting.message.server.subjects.ConnectionMonitoringSubject;
import com.dting.show.server.service.DtingEnvService;
import com.dting.show.server.service.DtingInstanceService;
import com.dting.show.server.service.DtingServerService;
import com.dting.show.server.utils.InstanceUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * *************************************************<br/>
 * 连接信息操作<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:11
 */
@SuppressWarnings("all")
public class ConnectionMonitoringMessageBufferReactor extends MessageBufferReactor<ConnectionMonitoringSubject> {

    /**
     * redis
     */
    private final StringRedisTemplate redisTemplate;
    /**
     * 环境信息
     */
    private final DtingEnvService dtingEnvService;
    /**
     * 服务信息
     */
    private final DtingServerService dtingServerService;
    /**
     * 实例信息
     */
    private final DtingInstanceService dtingInstanceService;

    public ConnectionMonitoringMessageBufferReactor(StringRedisTemplate redisTemplate, DtingEnvService dtingEnvService, DtingServerService dtingServerService, DtingInstanceService dtingInstanceService) {
        this.redisTemplate = redisTemplate;
        this.dtingEnvService = dtingEnvService;
        this.dtingServerService = dtingServerService;
        this.dtingInstanceService = dtingInstanceService;
    }

    @Override
    public void start0(List<ConnectionMonitoringSubject> sources) {
        if (CollUtil.isNotEmpty(sources)) {
            sources.forEach(source -> {
                String envName = source.getEnv();
                String serverKeyName = source.getServerKey();
                String instanceName = source.getInstanceName();
                Long timeout = source.getTimeout();
                InstanceUtil.findAndSaveAndCache(envName, serverKeyName, instanceName, timeout);
            });
        }
    }
}
