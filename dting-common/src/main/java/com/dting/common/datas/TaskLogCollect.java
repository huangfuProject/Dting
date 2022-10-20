package com.dting.common.datas;

import java.io.Serializable;

/**
 * 任务运行信息
 * <p>
 * 记录每一个任务运行时的状态
 *
 * @author huangfu
 * @date 2022年10月14日15:51:54
 */
public class TaskLogCollect implements Serializable {
    private static final long serialVersionUID = 217021693585491436L;

    /**
     * 线程池的名称
     */
    private String threadPoolName;

    /**
     * 任务的名称
     */
    private String taskName;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 是否成功
     */
    private Boolean success = true;

    /**
     * 是否被拒绝
     */
    private Boolean rejected = false;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 当前活跃的任务数量
     */
    private int activeCount;

    /**
     * 队列剩余容量
     */
    private int queueRemainingCapacity;

    /**
     * 队列长度
     */
    private int queueSize;

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getQueueRemainingCapacity() {
        return queueRemainingCapacity;
    }

    public void setQueueRemainingCapacity(int queueRemainingCapacity) {
        this.queueRemainingCapacity = queueRemainingCapacity;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "threadPoolName='" + threadPoolName + '\'' +
                ", taskName='" + taskName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", success=" + success +
                ", rejected=" + rejected +
                ", errorMsg='" + errorMsg + '\'' +
                ", activeCount=" + activeCount +
                ", queueRemainingCapacity=" + queueRemainingCapacity +
                ", queueSize=" + queueSize +
                '}';
    }
}