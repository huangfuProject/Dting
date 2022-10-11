package com.dting.message.server.config;

import com.dting.message.common.MessageCommunicationConfig;

/**
 * 服务端的配置项
 *
 * @author huangfu
 * @date 2022年10月11日09:29:32
 */
public class MessageServerConfig {

    private String host;

    private final MessageCommunicationConfig communicationConfig;

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
}
