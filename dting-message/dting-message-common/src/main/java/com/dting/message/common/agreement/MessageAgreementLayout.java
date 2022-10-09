package com.dting.message.common.agreement;

import io.netty.buffer.ByteBuf;

/**
 * *************************************************<br/>
 * 消息协议定义<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/9 9:15
 */
public interface MessageAgreementLayout {

    /**
     * 消息编码
     *
     * @param data 数据包
     * @param byteBuf 协议编排完成之后需要放入的容器
     */
    void messageEncode(ByteBuf byteBuf,  byte[] data);

    /**
     * 消息解码
     *
     * @param byteBuf 完整的消息体
     * @return 消息解码后的数据包
     */
    byte[] messageDecode(ByteBuf byteBuf);
}
