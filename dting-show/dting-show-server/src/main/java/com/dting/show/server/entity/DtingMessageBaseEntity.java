package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.dting.message.common.agreement.packet.DtingMessage;
import lombok.Data;

/**
 * 基类
 *
 * @author huangfu
 * @date 2022年10月19日13:32:52
 */
@Data
public class DtingMessageBaseEntity {

    /**
     * 主键
     */
    @TableId
    private Integer id;

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
     * 消息的来源地址
     */
    private String messageIp;

    /**
     * 数据采集的时间
     */
    private Long collectTime;

    /**
     * 公共信息设置
     *
     * @param dtingMessage 来自客户端的消息
     */
    public void commonDataSetting(DtingMessage dtingMessage) {
        //消息来源
        this.setMessageIp(dtingMessage.getMessageSourceAddress());
        //服务环境
        this.setServerEnv(dtingMessage.getServerEnv());
        //服务名称
        this.setServerKey(dtingMessage.getServerKey());
        //实例名称
        this.setInstanceKey(dtingMessage.getInstanceKey());
        //消息采集时间
        this.setCollectTime(System.currentTimeMillis());
    }
}
