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
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;
}
