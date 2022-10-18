package com.dting.message.common.agreement.packet;

/**
 * 消息定义的父类
 *
 * @author huangfu
 * @date 2022年10月11日08:22:33
 */
public class DtingMessage {

    private String messageTag;

    /**
     * 消息的唯一标识
     */
    private String unique;

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
}
