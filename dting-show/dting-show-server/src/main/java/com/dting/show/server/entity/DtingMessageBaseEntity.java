package com.dting.show.server.entity;

import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.show.server.dto.InstanceData;
import com.dting.show.server.utils.InstanceUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基类
 *
 * @author huangfu
 * @date 2022年10月19日13:32:52
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DtingMessageBaseEntity extends BaseDting {


    private static final long serialVersionUID = 7122544941697112753L;
    /**
     * 归属的实例的Id
     */
    private Integer instanceId;

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
        //消息采集时间
        this.setCollectTime(System.currentTimeMillis());

        InstanceData saveAndCache = InstanceUtil.findAndSaveAndCache(dtingMessage.getServerEnv(), dtingMessage.getServerKey(), dtingMessage.getInstanceKey(), dtingMessage.getTimeout());
        this.instanceId = saveAndCache.getDtingInstance().getId();
    }
}
