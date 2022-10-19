package com.dting.show.server.factorys;

import com.dting.show.server.buffers.MessageCpuDataBufferReactor;
import com.dting.show.server.service.MessageCpuDataService;
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
    public MessageCpuDataBufferReactor messageBufferReactor(MessageCpuDataService messageCpuDataService){
        return new MessageCpuDataBufferReactor(messageCpuDataService);
    }
}
