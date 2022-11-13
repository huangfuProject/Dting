package com.dting.message.client.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.message.client.DtingMessageClient;
import com.dting.message.client.config.MessageClientConfig;
import com.dting.message.common.Communication;
import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.utils.ChannelUtil;
import io.netty.channel.Channel;

import java.util.*;
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

    private static final Random RANDOM = new Random();


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
     * @param channel     通道
     * @param instanceKey 实例主键
     * @param serverEnv   服务环境
     * @param serverKey   服务主键
     */
    public static void addConnection(Channel channel, String instanceKey, String serverEnv, String serverKey, Long timeout) {
        Communication communication = new Communication(channel, instanceKey, serverEnv, serverKey, timeout);
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
        if (communication == null) {
            return;
        }
        CONNECTION_POOL.remove(communication.getAddress());
        communication.closeCommunication();
    }

    /**
     * 异步发送消息  随机负载
     *
     * @param message 消息对象
     */
    public static void asyncSendMessage(DtingMessage message) {
        Collection<Communication> communicationCollection = CONNECTION_POOL.values();
        if (CollectionUtil.isNotEmpty(communicationCollection)) {
            int size = communicationCollection.size();
            List<Communication> communications = new ArrayList<>(communicationCollection);
            int randomNum = RANDOM.nextInt(size);
            Communication communication = communications.get(randomNum);
            if (communication != null && communication.communicationStatus()) {
                communication.asyncSendMessage(message);
            } else {
                removeConnection(communication);
                asyncSendMessage(message);
            }
        }else {
            throw new IllegalArgumentException("没有可用的连接....");
        }
    }

    /**
     * 关闭全部的消息
     */
    public static void closeAll() {
        Collection<Communication> communicationCollection = CONNECTION_POOL.values();
        if (CollectionUtil.isNotEmpty(communicationCollection)) {
            communicationCollection.forEach(Communication::closeCommunication);
        }
        //清空连接缓存
        CONNECTION_POOL.clear();
    }


    /**
     * 开启全部的消息对象
     *
     * @param configList 配置集合
     */
    public static void startAll(List<MessageClientConfig> configList) {
        for (MessageClientConfig config : configList) {
            DtingMessageClient dtingMessageClient = new DtingMessageClient(config);
            dtingMessageClient.connect();
        }
    }

}
