package com.dting.show.datas;

import com.dting.message.common.agreement.packet.DtingMessage;

import java.io.Serializable;

/**
 * 任务信息消息对象
 *
 * @author huangfu
 * @date 2022年10月17日14:05:22
 */
public class TaskInfoMessage extends DtingMessage implements Serializable {

    private static final long serialVersionUID = 4429958958843848431L;

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
        final StringBuffer sb = new StringBuffer("TaskInfoMessage{");
        sb.append("threadPoolName='").append(threadPoolName).append('\'');
        sb.append(", taskName='").append(taskName).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", success=").append(success);
        sb.append(", rejected=").append(rejected);
        sb.append(", errorMsg='").append(errorMsg).append('\'');
        sb.append(", activeCount=").append(activeCount);
        sb.append(", queueRemainingCapacity=").append(queueRemainingCapacity);
        sb.append(", queueSize=").append(queueSize);
        sb.append('}');
        return sb.toString();
    }
}
