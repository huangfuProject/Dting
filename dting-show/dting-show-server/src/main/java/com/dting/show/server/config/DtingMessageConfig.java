package com.dting.show.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * dting服务配置
 *
 * @author huangfu
 * @date 2022年10月18日11:51:46
 */
@ConfigurationProperties(prefix = "dting.message.server")
public class DtingMessageConfig implements Serializable {

    private static final long serialVersionUID = 6261951652590381609L;

    /**
     * 通讯端口
     */
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
