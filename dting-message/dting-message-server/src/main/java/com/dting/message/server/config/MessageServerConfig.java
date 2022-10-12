package com.dting.message.server.config;

import com.dting.message.common.MessageCommunicationConfig;
import com.dting.message.common.agreement.packet.DtingMessage;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端的配置项
 *
 * @author huangfu
 * @date 2022年10月11日09:29:32
 */
public class MessageServerConfig {

    private String host;

    private final MessageCommunicationConfig communicationConfig;

    /**
     * 构建一个业务处理器 服务端
     */
    private final Map<String, SimpleChannelInboundHandler<? extends DtingMessage>> serverBusinessProcessingUnit = new ConcurrentHashMap<>(8);


    private final Integer port;


    public MessageServerConfig(String host, MessageCommunicationConfig communicationConfig, Integer port) {
        this.host = host;
        this.communicationConfig = communicationConfig;
        this.port = port;
    }

    public MessageServerConfig(MessageCommunicationConfig communicationConfig, Integer port) {
        this.communicationConfig = communicationConfig;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public MessageCommunicationConfig getCommunicationConfig() {
        return communicationConfig;
    }

    public Integer getPort() {
        return port;
    }

    /**
     * 追加一个服务端业务处理器
     *
     * @param businessProcessingName 业务处理器的名称
     * @param handler                处理器
     */
    public void addServerBusinessProcessing(String businessProcessingName, SimpleChannelInboundHandler<? extends DtingMessage> handler) {
        serverBusinessProcessingUnit.put(businessProcessingName, handler);
    }


    /**
     * 追加一个服务端业务处理器  默认的名称是类名
     *
     * @param handler 处理器
     */
    public void addServerBusinessProcessing(SimpleChannelInboundHandler<? extends DtingMessage> handler) {
        this.addServerBusinessProcessing(handler.getClass().getSimpleName(), handler);
    }

    /**
     * 返回构建的所有的业务处理器
     *
     * @return 业务处理器的拷贝对象
     */
    public Map<String, SimpleChannelInboundHandler<? extends DtingMessage>> getServerBusinessProcessingUnit() {
        Map<String, SimpleChannelInboundHandler<? extends DtingMessage>> businessProcessingUnitCopy = new ConcurrentHashMap<>(8);
        businessProcessingUnitCopy.putAll(serverBusinessProcessingUnit);
        return businessProcessingUnitCopy;
    }
}
