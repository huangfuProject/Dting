package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
    private String unique;

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
}
