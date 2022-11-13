package com.dting.message.common.agreement.packet;

import com.dting.message.common.agreement.packet.DtingMessage;

import java.io.Serializable;

/**
 * 客户端连接的消息
 *
 * @author huangfu
 * @date 2022年10月12日09:27:17
 */
public class ConnectionMessage extends DtingMessage implements Serializable {
    private static final long serialVersionUID = 4659504775850578237L;
    /**
     * 连接状态
     */
    private ConnectionStatus connectionStatus = ConnectionStatus.OPEN;


    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public enum ConnectionStatus {
        /**
         * 开启
         */
        OPEN,
        /**
         * 销毁
         */
        CLOSE
    }
}

