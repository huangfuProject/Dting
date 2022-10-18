package com.dting.message.client.config;

import com.dting.message.common.DefaultMessageCommunicationConfig;
import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息服务客户端的连接信息
 *
 * @author huangfu
 * @date 2022年10月12日18:21:33
 */
public class MessageClientConfig implements Serializable {

    private static final long serialVersionUID = -2512507004910424251L;

    /**
     * 主机地址
     */
    private final String host;

    /**
     * 协议配置项
     */
    private final DefaultMessageCommunicationConfig communicationConfig;

    /**
     * 服务端端口
     */
    private final Integer port;

    /**
     * 消息的标签
     */
    private String messageTag;

    /**
     * 构建一个业务处理器 客户端
     */
    private final Map<String, DtingSimpleChannelInboundHandler<? extends DtingMessage>> clientBusinessProcessingUnit = new ConcurrentHashMap<>(8);


    public MessageClientConfig(String host, DefaultMessageCommunicationConfig communicationConfig, Integer port) {
        this.host = host;
        this.communicationConfig = communicationConfig;
        this.port = port;
    }

    public MessageClientConfig(String host, Integer port) {
        this(host, new DefaultMessageCommunicationConfig(), port);
    }

    public String getHost() {
        return host;
    }

    public DefaultMessageCommunicationConfig getCommunicationConfig() {
        return communicationConfig;
    }

    public Integer getPort() {
        return port;
    }

    /**
     * 追加一个客户端业务处理器  默认的名称是类名
     *
     * @param handler 处理器
     */
    public void addClientBusinessProcessing(DtingSimpleChannelInboundHandler<? extends DtingMessage> handler) {
        this.addClientBusinessProcessing(handler.getClass().getSimpleName(), handler);
    }

    /**
     * 追加一个客户端业务处理器
     *
     * @param businessProcessingName 名称
     * @param handler                处理器
     */
    public void addClientBusinessProcessing(String businessProcessingName, DtingSimpleChannelInboundHandler<? extends DtingMessage> handler) {
        this.clientBusinessProcessingUnit.put(businessProcessingName, handler);
    }

    /**
     * 返回构建的所有的业务处理器
     *
     * @return 业务处理器的拷贝对象
     */
    public Map<String, DtingSimpleChannelInboundHandler<? extends DtingMessage>> getClientBusinessProcessingUnit() {
        Map<String, DtingSimpleChannelInboundHandler<? extends DtingMessage>> businessProcessingUnitCopy = new ConcurrentHashMap<>(8);
        businessProcessingUnitCopy.putAll(clientBusinessProcessingUnit);
        return businessProcessingUnitCopy;
    }

    public String getMessageTag() {
        return messageTag;
    }

    public void setMessageTag(String messageTag) {
        this.messageTag = messageTag;
    }
}
