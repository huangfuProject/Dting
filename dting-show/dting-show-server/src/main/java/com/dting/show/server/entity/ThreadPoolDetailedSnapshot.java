package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 线程池的状态采集每一个线程池的状态信息
 *
 * @author huangfu
 * @date 2022年10月20日15:09:06
 */
@Data
@TableName("thread_pool_detailed_snapshot")
public class ThreadPoolDetailedSnapshot {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 线程池的名称
     */
    private String threadPoolName;

    /**
     * 活跃的数量
     */
    private Integer activeCount;
    /**
     * 核心的数量
     */
    private Integer coreCount;

    /**
     * 最大允许的数量
     */
    private Integer maxCount;

    /**
     * 队列使用的数量
     */
    private Integer queueUseCount;

    /**
     * 队列总长度
     */
    private Integer queueTotalSize;

    /**
     * 繁忙指标
     */
    private Double busyWeight;
}
