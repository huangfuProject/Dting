package com.dting.show.server.conditions;

import lombok.Data;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 实例查询条件<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/7 16:48
 */
@Data
public class InstanceCondition implements Serializable {
    private static final long serialVersionUID = 1436066793206671843L;

    /**
     * 环境主键
     */
    private Integer envId;

    /**
     * 实例名称
     */
    private String instanceRegularName;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

}
