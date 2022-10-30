package com.dting.show.server.vos.monitoring;

import cn.hutool.core.collection.CollectionUtil;

import java.io.Serializable;
import java.util.List;

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

    /**
     * 对应的数据为空
     *
     * @return 为空
     */
    public boolean isEmpty() {
        List<JvmMemoryData> jvmMemoryDataList = memoryDataVo.getJvmMemoryDataList();
        List<SystemMemoryData> systemMemoryDataList = memoryDataVo.getSystemMemoryDataList();
        List<SystemSwapData> systemSwapDataList = memoryDataVo.getSystemSwapDataList();
        return CollectionUtil.isEmpty(jvmMemoryDataList) && CollectionUtil.isEmpty(systemMemoryDataList) && CollectionUtil.isEmpty(systemSwapDataList);
    }

    public MemoryDataVo getMemoryDataVo() {
        return memoryDataVo;
    }

    public void setMemoryDataVo(MemoryDataVo memoryDataVo) {
        this.memoryDataVo = memoryDataVo;
    }
}
