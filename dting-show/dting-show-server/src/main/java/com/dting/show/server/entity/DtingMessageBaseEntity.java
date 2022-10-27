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
     * 消息唯一key
     */
    private String uniqueKey;

    /**
     * 消息的标签
     */
    private String messageTag;

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
        //消息标签
        this.setMessageTag(dtingMessage.getMessageTag());
        //消息的唯一值
        this.setUniqueKey(dtingMessage.getUnique());
        //消息采集时间
        this.setCollectTime(System.currentTimeMillis());
    }
}
