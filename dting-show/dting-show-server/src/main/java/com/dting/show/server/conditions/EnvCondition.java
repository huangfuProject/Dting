package com.dting.show.server.conditions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 环境信息的查询条件<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/3 13:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvCondition  implements Serializable {

    private static final long serialVersionUID = -5302318139623924073L;
    /**
     * 环境的正则表达式名称
     */
    private String envRegularName;

    /**
     * 服务id
     */
    private Integer serverId;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;
}
