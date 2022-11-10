package com.dting.show.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 线程池配置对象的Dto
 *
 * @author huangfu
 * @date 2022/11/10 8:32
 */
@Data
public class ThreadPoolConfigDto implements Serializable {
    private static final long serialVersionUID = -5598307593217542278L;
    /**
     * 要修改的id
     */
    private Integer id;

    /**
     * 核心数
     */
    private Integer coreCount;

    /**
     * 最大线程数
     */
    private Integer maxCount;

    /**
     * 拒绝策略名称
     */
    private String rejectHandlerName;

    /**
     * 线程休眠时间
     */
    private Long keepAliveTime;
}
