package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务执行日志
 *
 * @author huangfu
 * @date 2022年10月20日11:24:15
 */
@Data
@TableName("message_task_run_snapshot")
@EqualsAndHashCode(callSuper = true)
public class MessageTaskRunSnapshot extends DtingMessageBaseEntity {
    /**
     * 线程池名称
     */
    private String threadPoolName;

    /**
     * 线程池的组名称 拼接规则为 message_ip:thread_pool_name
     */
    private String threadPoolGroupName;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 开始的时间戳
     */
    private Long startTime;

    /**
     * 结束的时间戳
     */
    private Long endTime;

    /**
     * 任务执行是否成功  0成功  1失败
     */
    private String success;

    /**
     * 任务是否被拒绝     0未拒绝  1 拒绝
     */
    private String rejected;

    /**
     * 如果是失败了，记录一个失败信息
     */
    private String errorMsg;

    /**
     * 当前活跃的任务数量
     */
    private Integer activeCount;

    /**
     * 当前队列的剩余容量
     */
    private Integer queueRemainingCapacity;

    /**
     * 当前队列已经使用的容量
     */
    private Integer queueSize;
}
