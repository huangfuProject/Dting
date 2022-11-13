package com.dting.message.server.subjects;

import com.dting.subject.Subject;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 连接监视主题 <br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/12 16:16
 */
public class ConnectionMonitoringSubject extends Subject implements Serializable {
    private static final long serialVersionUID = 8631528961273963988L;

    public ConnectionMonitoringSubject(String env, String serverKey, String instanceName, Long timeout) {
        this.env = env;
        this.serverKey = serverKey;
        this.instanceName = instanceName;
    }

    /**
     * 环境信息
     */
    private String env;

    /**
     * 服务信息
     */
    private String serverKey;

    /**
     * 实例名称
     */
    private String instanceName;

    /**
     * 实例超时时间
     */
    private Long timeout;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "ConnectionMonitoringSubject{" +
                "env='" + env + '\'' +
                ", serverKey='" + serverKey + '\'' +
                ", instanceName='" + instanceName + '\'' +
                '}';
    }
}
