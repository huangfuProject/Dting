package com.dting.message.common.agreement.packet;

/**
 * 消息定义的父类
 *
 * @author huangfu
 * @date 2022年10月11日08:22:33
 */
public class DtingMessage {

    /**
     * 获取当前类的类型
     *
     * @return 返回具体实现的类的全限定名
     */
    public final String classType() {
        return this.getClass().getName();
    }
}
