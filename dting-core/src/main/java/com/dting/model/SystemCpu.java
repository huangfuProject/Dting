package com.dting.model;

import java.io.Serializable;

/**
 * 单核CPU状态
 *
 * @author huangfu
 * @date 2022年10月17日09:18:13
 */
public class SystemCpu extends SystemCpuInfo implements Serializable {
    private static final long serialVersionUID = 399684400463418035L;

    private Integer cpuSort;
    /**
     * cpu频率
     */
    private Integer cpuMhz;

    /**
     * cpu厂商
     */
    private String cpuSuper;

    /**
     * cpu类别
     */
    private String cpuType;

    /**
     * cpu缓存器数量
     */
    private Long cpuCacheCount;


    public Integer getCpuSort() {
        return cpuSort;
    }

    public void setCpuSort(Integer cpuSort) {
        this.cpuSort = cpuSort;
    }

    public Integer getCpuMhz() {
        return cpuMhz;
    }

    public void setCpuMhz(Integer cpuMhz) {
        this.cpuMhz = cpuMhz;
    }

    public String getCpuSuper() {
        return cpuSuper;
    }

    public void setCpuSuper(String cpuSuper) {
        this.cpuSuper = cpuSuper;
    }

    public String getCpuType() {
        return cpuType;
    }

    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    public Long getCpuCacheCount() {
        return cpuCacheCount;
    }

    public void setCpuCacheCount(Long cpuCacheCount) {
        this.cpuCacheCount = cpuCacheCount;
    }
}
