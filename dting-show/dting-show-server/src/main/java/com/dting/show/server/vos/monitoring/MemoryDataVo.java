package com.dting.show.server.vos.monitoring;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 内存数据VO
 *
 * @author huangfu
 * @date 2022年10月28日15:34:07
 */
@Data
public class MemoryDataVo implements Serializable {
    private static final long serialVersionUID = 5840542101463789141L;
    /**
     * 内存数据
     */
    private final List<SystemMemoryData> systemMemoryDataList = new ArrayList<>();

    /**
     * jvm内存数据
     */
    private final List<JvmMemoryData> jvmMemoryDataList = new ArrayList<>();

    /**
     * 系统交换区内存数据
     */
    private final List<SystemSwapData> systemSwapDataList = new ArrayList<>();

    /**
     * 添加系统内存数据
     *
     * @param systemMemoryData 系统内存数据
     */
    public void addSystemMemoryData(SystemMemoryData systemMemoryData) {
        this.systemMemoryDataList.add(systemMemoryData);
    }

    /**
     * 添加jvm内存数据
     *
     * @param jvmMemoryData jvm内存数据
     */
    public void addJvmMemoryData(JvmMemoryData jvmMemoryData) {
        this.jvmMemoryDataList.add(jvmMemoryData);
    }

    /**
     * 添加交换区数据
     *
     * @param systemSwapData 交换区数据
     */
    public void addSystemSwapData(SystemSwapData systemSwapData) {
        this.systemSwapDataList.add(systemSwapData);
    }
}
