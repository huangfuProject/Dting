package com.dting.show.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * *************************************************<br/>
 * 实例信息<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DtingInstance extends BaseDting {
    private static final long serialVersionUID = -2396282205917296600L;

    /**
     * 实例名称
     */
    private String instanceName;

    /**
     * 实例环境
     */
    private Integer envId;

    /**
     * 状态
     */
    private String state;

    /**
     * 超时时间
     */
    private Long timeout;

    /**
     * 最后一次更新时间
     */
    private Long lastUpdateTime;

    /**
     * 创建时间
     */
    private Long createDate;
}
