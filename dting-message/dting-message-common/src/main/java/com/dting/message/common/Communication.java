package com.dting.message.common;

import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.utils.ChannelUtil;
import io.netty.channel.Channel;

import java.io.Serializable;
import java.util.Objects;

/**
 * 通讯器
 *
 * @author huangfu
 * @date 2022年10月12日08:40:03
 */
public class Communication implements Serializable {

    private static final long serialVersionUID = 7447817019425981014L;
    /**
     * 通讯的管道
     */
    private final Channel channel;

    /**
     * 远程主机地址
     */
    private final String address;


    /**
     * 实例名称
     */
    private final String instanceKey;

    /**
     * 服务的环境
     */
    private final String serverEnv;

    /**
     * 服务的名称
     */
    private final String serverKey;

    public Communication(Channel channel, String instanceKey, String serverEnv, String serverKey) {
        this.channel = channel;
        this.address = ChannelUtil.getChannelRemoteAddress(channel);
        this.instanceKey = instanceKey;
        this.serverEnv = serverEnv;
        this.serverKey = serverKey;
    }

    /**
     * 异步的发送一个消息
     *
     * @param dtingMessage 消息体
     */
    public void asyncSendMessage(DtingMessage dtingMessage) {
        dtingMessage.setServerEnv(serverEnv);
        dtingMessage.setInstanceKey(instanceKey);
        dtingMessage.setServerKey(serverKey);
        channel.writeAndFlush(dtingMessage);
    }

    /**
     * 关闭连接
     */
    public void closeCommunication() {
        if (communicationStatus()) {
            channel.closeFuture().syncUninterruptibly();
        }
    }

    /**
     * 判断通道是否活跃
     *
     * @return 是否活跃
     */
    public boolean communicationStatus() {
        return channel.isOpen() && channel.isActive();
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Communication)) {
            return false;
        }

        Communication that = (Communication) o;

        if (!Objects.equals(channel, that.channel)) {
            return false;
        }
        return getAddress() != null ? getAddress().equals(that.getAddress()) : that.getAddress() == null;
    }

    @Override
    public int hashCode() {
        int result = channel != null ? channel.hashCode() : 0;
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        return result;
    }
}
