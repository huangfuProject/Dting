package com.dting.show.datas;

import com.dting.message.common.agreement.packet.DtingMessage;

import java.io.Serializable;

/**
 * 线程池详细信息
 *
 * @author huangfu
 * @date 2022年10月19日08:49:59
 */
public class ThreadPoolDetailedMessage extends DtingMessage implements Serializable {
    private static final long serialVersionUID = -153906139415106891L;
    /**
     * 线程池名称
     */
    private String poolName;

    /**
     * 核心数量
     */
    private int coreSize;

    /**
     * 最大数量
     */
    private int maxSize;

    /**
     * 队列总长度
     */
    private int queueTotalSize;

    /**
     * 拒绝策略
     */
    private String rejectHandlerName;

    /**
     * 队列名称
     */
    private String queueTypeName;

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
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

    public int getQueueTotalSize() {
        return queueTotalSize;
    }

    public void setQueueTotalSize(int queueTotalSize) {
        this.queueTotalSize = queueTotalSize;
    }

    public String getRejectHandlerName() {
        return rejectHandlerName;
    }

    public void setRejectHandlerName(String rejectHandlerName) {
        this.rejectHandlerName = rejectHandlerName;
    }

    public String getQueueTypeName() {
        return queueTypeName;
    }

    public void setQueueTypeName(String queueTypeName) {
        this.queueTypeName = queueTypeName;
    }
}
