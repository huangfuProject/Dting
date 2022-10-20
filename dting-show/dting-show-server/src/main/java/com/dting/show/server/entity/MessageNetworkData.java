package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 网卡数据
 *
 * @author huangfu
 * @date 2022年10月20日10:54:49
 */
@Data
@TableName("message_network_data")
@EqualsAndHashCode(callSuper = true)
public class MessageNetworkData extends DtingMessageBaseEntity implements Serializable {
    private static final long serialVersionUID = 5630286540437125981L;

    /**
     * 网卡数据
     */
    private String networkDataKey;
}
