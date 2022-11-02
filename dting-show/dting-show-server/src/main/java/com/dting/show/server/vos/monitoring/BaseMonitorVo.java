package com.dting.show.server.vos.monitoring;

import lombok.Data;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 监控相关的VO的基类<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/2 21:23
 */
@Data
public class BaseMonitorVo implements Serializable {

    private static final long serialVersionUID = -2552610321249283658L;
    /**
     * 监听的id
     */
    private String monitorId;
}
