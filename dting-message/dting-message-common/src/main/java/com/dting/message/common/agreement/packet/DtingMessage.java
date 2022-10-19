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
    private String messageTag;

    /**
     * 消息的唯一标识
     */
    private String unique;

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

    public String getMessageTag() {
        return messageTag;
    }

    public void setMessageTag(String messageTag) {
        this.messageTag = messageTag;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getMessageSourceAddress() {
        return messageSourceAddress;
    }

    public void setMessageSourceAddress(String messageSourceAddress) {
        this.messageSourceAddress = messageSourceAddress;
    }
}
