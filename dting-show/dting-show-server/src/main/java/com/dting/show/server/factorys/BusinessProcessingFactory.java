package com.dting.show.server.factorys;

import com.dting.show.server.processing.SystemInfoBusinessProcessing;
import com.dting.show.server.processing.TaskInfoServerBusinessProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息处理器
 *
 * @author huangfu
 * @date 2022年10月18日11:47:08
 */
@Configuration
public class BusinessProcessingFactory {
    @Bean
    public SystemInfoBusinessProcessing systemInfoBusinessProcessing(){
        return new SystemInfoBusinessProcessing();
    }

    @Bean
    public TaskInfoServerBusinessProcessing taskInfoServerBusinessProcessing(){
        return new TaskInfoServerBusinessProcessing();
    }
}
