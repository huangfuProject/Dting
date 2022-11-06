package com.dting.common.datas;

import java.io.Serializable;

/**
 * 线程池的信息  这里是线程池的即时快照，即在某一刻的线程池的负载状态
 *
 * @author huangfu
 * @date 2022年10月18日17:53:18
 */
public class ThreadPoolDataCollect implements Serializable {
    private static final long serialVersionUID = -6099899546413460243L;

    /**
     * 线程池名称
     */
    private String poolName;

    /**
     * 活跃数量
     */
    private int activeSize;

    /**
     * 核心数
     */
    private int coreSize;

    /**
     * 最大数量
     */
    private int maxSize;


    /**
     * 队列类型名称
     */
    private String queueTypeName;

    /**
     * 队列使用的大小
     */
    private int queueUseSize;

    /**
     * 队列剩余大小
     */
    private int queueRemainingCapacity;

    /**
     * 完成的任务数
     */
    private long currentRunningCompletedTaskCount;

    /**
     * 拒绝的数量
     */
    private long currentRunningRejectCount;

    /**
     * 拒绝策略的名称
     */
    private String rejectHandlerName;

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public int getActiveSize() {
        return activeSize;
    }

    public void setActiveSize(int activeSize) {
        this.activeSize = activeSize;
    }

    public int getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getQueueTypeName() {
        return queueTypeName;
    }

    public void setQueueTypeName(String queueTypeName) {
        this.queueTypeName = queueTypeName;
    }

    public int getQueueUseSize() {
        return queueUseSize;
    }

    public void setQueueUseSize(int queueUseSize) {
        this.queueUseSize = queueUseSize;
    }

    public int getQueueRemainingCapacity() {
        return queueRemainingCapacity;
    }

    public void setQueueRemainingCapacity(int queueRemainingCapacity) {
        this.queueRemainingCapacity = queueRemainingCapacity;
    }


    public long getCurrentRunningCompletedTaskCount() {
        return currentRunningCompletedTaskCount;
    }

    public void setCurrentRunningCompletedTaskCount(long currentRunningCompletedTaskCount) {
        this.currentRunningCompletedTaskCount = currentRunningCompletedTaskCount;
    }

    public long getCurrentRunningRejectCount() {
        return currentRunningRejectCount;
    }

    public void setCurrentRunningRejectCount(long currentRunningRejectCount) {
        this.currentRunningRejectCount = currentRunningRejectCount;
    }

    public String getRejectHandlerName() {
        return rejectHandlerName;
    }

    public void setRejectHandlerName(String rejectHandlerName) {
        this.rejectHandlerName = rejectHandlerName;
    }
}
