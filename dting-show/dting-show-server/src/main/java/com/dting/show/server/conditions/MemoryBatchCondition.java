package com.dting.show.server.conditions;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统级内存的查询条件
 *
 * @author huangfu
 * @date 2022年10月27日08:37:38
 */
@Data
public class MemoryBatchCondition implements Serializable {

    private static final long serialVersionUID = -5028176883744292629L;
    /**
     * 大于此id
     */
    private Integer id;

    /**
     * 消息标签
     */
    private String messageTag;

    /**
     * 要查询的ip
     */
    private String address;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;
}
