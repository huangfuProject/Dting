package com.dting.show.server.factorys;

import com.dting.show.server.buffers.SystemInfoDataBufferReactor;
import com.dting.show.server.processing.SystemInfoBusinessProcessing;
import com.dting.show.server.processing.TaskInfoServerBusinessProcessing;
import com.dting.show.server.processing.ThreadPoolDetailedBusinessProcessing;
import com.dting.show.server.processing.ThreadPoolInfoServerBusinessProcessing;
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

    /**
     * 针对 系统信息的采集器
     *
     * @return 系统当前的运行状态
     */
    @Bean
    public SystemInfoBusinessProcessing systemInfoBusinessProcessing(SystemInfoDataBufferReactor systemInfoDataBufferReactor) {
        return new SystemInfoBusinessProcessing(systemInfoDataBufferReactor);
    }

    /**
     * 采集当前 执行线程池中每一个任务的执行信息采集的处理器
     *
     * @return 任务的执行信息采集数据的处理器
     */
    @Bean
    public TaskInfoServerBusinessProcessing taskInfoServerBusinessProcessing() {
        return new TaskInfoServerBusinessProcessing();
    }

    /**
     * 针对于线程池状态采集数据的处理器
     *
     * @return 针对于线程池状态采集数据的处理器
     */
    @Bean
    public ThreadPoolInfoServerBusinessProcessing threadPoolInfoServerBusinessProcessing() {
        return new ThreadPoolInfoServerBusinessProcessing();
    }

    /**
     * 线上线程池的状态上报处理器
     *
     * @return 线上线程池的状态上报处理器
     */
    @Bean
    public ThreadPoolDetailedBusinessProcessing threadPoolDetailedBusinessProcessing() {
        return new ThreadPoolDetailedBusinessProcessing();
    }
}
