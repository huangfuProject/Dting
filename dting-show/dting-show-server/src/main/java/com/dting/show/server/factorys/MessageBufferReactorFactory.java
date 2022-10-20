package com.dting.show.server.factorys;

import com.dting.show.server.buffers.SystemInfoDataBufferReactor;
import com.dting.show.server.buffers.TaskRunLogDataBufferReactor;
import com.dting.show.server.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
