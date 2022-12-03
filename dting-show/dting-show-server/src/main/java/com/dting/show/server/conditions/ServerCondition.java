package com.dting.show.server.conditions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 服务列表查询条件<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/3 13:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerCondition implements Serializable {
    private static final long serialVersionUID = 473007345758870493L;

    private Integer envId;

    /**
     * 服务的正则表达式名称
     */
    private String serverRegularName;
    
    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;
}
