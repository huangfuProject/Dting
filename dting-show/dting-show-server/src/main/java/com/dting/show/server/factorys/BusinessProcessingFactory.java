package com.dting.show.server.factorys;

import com.dting.show.server.buffers.SystemInfoDataBufferReactor;
import com.dting.show.server.buffers.TaskRunLogDataBufferReactor;
import com.dting.show.server.buffers.ThreadPoolDataBufferReactor;
import com.dting.show.server.buffers.ThreadPoolDetailedConfigReactor;
import com.dting.show.server.processing.SystemInfoBusinessProcessing;
import com.dting.show.server.processing.TaskInfoServerBusinessProcessing;
import com.dting.show.server.processing.ThreadPoolDetailedBusinessProcessing;
import com.dting.show.server.processing.ThreadPoolDataCollectServerBusinessProcessing;
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
    public TaskInfoServerBusinessProcessing taskInfoServerBusinessProcessing(TaskRunLogDataBufferReactor taskRunLogDataBufferReactor) {
        return new TaskInfoServerBusinessProcessing(taskRunLogDataBufferReactor);
    }

    /**
     * 针对于线程池状态采集数据的处理器
     *
     * @return 针对于线程池状态采集数据的处理器
     */
    @Bean
    public ThreadPoolDataCollectServerBusinessProcessing threadPoolInfoServerBusinessProcessing(ThreadPoolDataBufferReactor threadPoolDataBufferReactor) {
        return new ThreadPoolDataCollectServerBusinessProcessing(threadPoolDataBufferReactor);
    }

    /**
     * 线上线程池的状态上报处理器
     *
     * @return 线上线程池的状态上报处理器
     */
    @Bean
    public ThreadPoolDetailedBusinessProcessing threadPoolDetailedBusinessProcessing(ThreadPoolDetailedConfigReactor threadPoolDetailedConfigReactor) {
        return new ThreadPoolDetailedBusinessProcessing(threadPoolDetailedConfigReactor);
    }
}
