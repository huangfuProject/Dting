package com.dting.show.server.factorys;

import com.dting.show.server.buffers.*;
import com.dting.show.server.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 反应堆的工厂
 *
 * @author huangfu
 * @date 2022年10月19日16:49:45
 */
@Configuration
public class MessageBufferReactorFactory {

    @Bean
    public SystemInfoDataBufferReactor systemInfoDataBufferReactor(
            MessageCpuSnapshotService messageCpuSnapshotService,
            MessageMemorySnapshotService messageMemorySnapshotService,
            MessageNetworkDataService messageNetworkDataService,
            NetworkDetailedSnapshotService networkDetailedSnapshotService
    ) {
        return new SystemInfoDataBufferReactor(messageCpuSnapshotService, messageMemorySnapshotService, messageNetworkDataService, networkDetailedSnapshotService);
    }

    @Bean
    public TaskRunLogDataBufferReactor taskRunLogDataBufferReactor(MessageTaskRunSnapshotService messageTaskRunSnapshotService) {
        return new TaskRunLogDataBufferReactor(messageTaskRunSnapshotService);

    }

    @Bean
    public ThreadPoolDataBufferReactor threadPoolDataBufferReactor(
            ThreadPoolDetailedSnapshotService threadPoolDetailedSnapshotService,
            MessageThreadPoolSnapshotService messageThreadPoolSnapshotService) {
        return new ThreadPoolDataBufferReactor(threadPoolDetailedSnapshotService, messageThreadPoolSnapshotService);
    }

    @Bean
    public ThreadPoolDetailedConfigReactor threadPoolDetailedConfigReactor(ThreadPoolConfigService threadPoolConfigService) {
        return new ThreadPoolDetailedConfigReactor(threadPoolConfigService);
    }

    @Bean
    public ConnectionMonitoringMessageBufferReactor connectionMonitoringMessageBufferReactor(StringRedisTemplate stringRedisTemplate, DtingEnvService dtingEnvService, DtingServerService dtingServerService, DtingInstanceService dtingInstanceService) {
        return new ConnectionMonitoringMessageBufferReactor(stringRedisTemplate, dtingEnvService, dtingServerService, dtingInstanceService);
    }
}
