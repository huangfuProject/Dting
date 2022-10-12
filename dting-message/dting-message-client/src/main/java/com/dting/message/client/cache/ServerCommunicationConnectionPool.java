package com.dting.message.client.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.message.common.Communication;
import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.utils.ChannelUtil;
import io.netty.channel.Channel;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接池  主要存储连接的服务端的通道
 *
 * @author huangfu
 * @date 2022年10月12日08:45:00
 */
public class ServerCommunicationConnectionPool {

    /**
     * 通讯器的连接对象
     */
    private final static Map<String, Communication> CONNECTION_POOL = new ConcurrentHashMap<>(8);

    /**
     * 追加一个通讯器
     *
     * @param communication 通讯器
     */
    public static void addConnection(Communication communication) {
        CONNECTION_POOL.put(communication.getAddress(), communication);
    }

    /**
     * 追加一个通讯器
     *
     * @param channel 通道
     */
    public static void addConnection(Channel channel) {
        Communication communication = new Communication(channel);
        CONNECTION_POOL.put(communication.getAddress(), communication);
    }

    /**
     * 销毁一个连接
     *
     * @param address 要销毁的地址
     */
    public static void removeConnection(String address) {
        Communication communication = CONNECTION_POOL.remove(address);
        communication.closeCommunication();
    }

    /**
     * 销毁一个连接
     *
     * @param channel 要销毁的通道
     */
    public static void removeConnection(Channel channel) {
        Communication communication = CONNECTION_POOL.remove(ChannelUtil.getChannelRemoteAddress(channel));
        communication.closeCommunication();
    }

    /**
     * 销毁一个连接
     *
     * @param communication 要销毁的通讯器
     */
    public static void removeConnection(Communication communication) {
        CONNECTION_POOL.remove(communication.getAddress());
        communication.closeCommunication();
    }

    /**
     * 异步群发消息
     *
     * @param message 消息对象
     */
    public static void asyncAllSendMessage(DtingMessage message) {
        Collection<Communication> communicationCollection = CONNECTION_POOL.values();
        if(CollectionUtil.isNotEmpty(communicationCollection)) {
            communicationCollection.forEach(communication -> communication.asyncSendMessage(message));
        }

    }


}