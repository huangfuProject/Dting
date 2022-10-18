package com.dting.sdk.reactor;

import com.dting.message.common.agreement.packet.DtingMessage;

import java.io.Serializable;

/**
 * 待发送消息的包装体
 *
 * @author huangfu
 * @date 2022年10月17日14:34:15
 */
public class MessageBuffer implements Serializable {
    private static final long serialVersionUID = 4347918273048141458L;
    /**
     * 消息体
     */
    private DtingMessage dtingMessage;

    public MessageBuffer() {
    }

    public MessageBuffer(DtingMessage dtingMessage) {
        this.dtingMessage = dtingMessage;
    }

    public DtingMessage getDtingMessage() {
        return dtingMessage;
    }

    public void setDtingMessage(DtingMessage dtingMessage) {
        this.dtingMessage = dtingMessage;
    }
}
