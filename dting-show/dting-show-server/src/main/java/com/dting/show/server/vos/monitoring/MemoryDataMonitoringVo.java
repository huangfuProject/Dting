package com.dting.show.server.vos.monitoring;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 内存数据websocket监听设置<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/29 19:33
 */
public class MemoryDataMonitoringVo implements Serializable {
    private static final long serialVersionUID = 6646028867120718917L;

    /**
     * 内存数据
     */
    private MemoryDataVo memoryDataVo;

    /**
     * 监听的id
     */
    private String monitorId;

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public MemoryDataVo getMemoryDataVo() {
        return memoryDataVo;
    }

    public void setMemoryDataVo(MemoryDataVo memoryDataVo) {
        this.memoryDataVo = memoryDataVo;
    }
}
