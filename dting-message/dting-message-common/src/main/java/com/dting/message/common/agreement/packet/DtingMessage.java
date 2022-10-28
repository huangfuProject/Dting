package com.dting.message.common.agreement.packet;

import java.io.Serializable;

/**
 * 消息定义的父类
 *
 * @author huangfu
 * @date 2022年10月11日08:22:33
 */
public class DtingMessage implements Serializable {

    private static final long serialVersionUID = -60259995547914226L;
    /**
     * 实例名称
     */
    private String instanceKey;

    /**
     * 服务的环境
     */
    private String serverEnv;

    /**
     * 服务的名称
     */
    private String serverKey;

    /**
     * 消息来源ip
     */
    private String messageSourceAddress;

    /**
     * 获取当前类的类型
     *
     * @return 返回具体实现的类的全限定名
     */
    public final String classType() {
        return this.getClass().getName();
    }


    public String getMessageSourceAddress() {
        return messageSourceAddress;
    }

    public void setMessageSourceAddress(String messageSourceAddress) {
        this.messageSourceAddress = messageSourceAddress;
    }

    public String getInstanceKey() {
        return instanceKey;
    }

    public void setInstanceKey(String instanceKey) {
        this.instanceKey = instanceKey;
    }

    public String getServerEnv() {
        return serverEnv;
    }

    public void setServerEnv(String serverEnv) {
        this.serverEnv = serverEnv;
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }
}
