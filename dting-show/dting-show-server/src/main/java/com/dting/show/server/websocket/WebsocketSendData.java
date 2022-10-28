package com.dting.show.server.websocket;

import lombok.Data;

/**
 * websocket发送的数据
 *
 * @author huangfu
 * @date 2022年10月26日16:47:21
 */
@Data
public class WebsocketSendData {

    public static final String LISTENING = "0";

    /**
     * 该id证明websocket的身份
     */
    private String sessionId;

    /**
     * 意向类型   0为监听各类数据   否则为其他类型
     */
    private String purpose = LISTENING;

    /**
     * 实例名称
     */
    private String instanceKey;

    /**
     * 服务的环境
     */
    private String serverEnv;

    /**
     * 服务的名称
     */
    private String serverKey;

    /**
     * 查看的地址
     */
    private String address;

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 结束时间
     */
    private long endTime;
}


