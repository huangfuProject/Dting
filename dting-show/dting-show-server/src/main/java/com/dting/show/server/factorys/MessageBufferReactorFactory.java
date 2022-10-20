package com.dting.show.server.factorys;

import com.dting.show.server.buffers.SystemInfoDataBufferReactor;
import com.dting.show.server.buffers.TaskRunLogDataBufferReactor;
import com.dting.show.server.service.MessageCpuDataService;
import com.dting.show.server.service.MessageMemoryDataService;
import com.dting.show.server.service.MessageNetworkDataService;
import com.dting.show.server.service.MessageTaskRunLogDataService;
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
            MessageCpuDataService messageCpuDataService,
            MessageMemoryDataService messageMemoryDataService,
            MessageNetworkDataService messageNetworkDataService
    ) {
        return new SystemInfoDataBufferReactor(messageCpuDataService, messageMemoryDataService, messageNetworkDataService);
    }

    @Bean
    public TaskRunLogDataBufferReactor taskRunLogDataBufferReactor(MessageTaskRunLogDataService messageTaskRunLogDataService) {
        return new TaskRunLogDataBufferReactor(messageTaskRunLogDataService);

    }
}
