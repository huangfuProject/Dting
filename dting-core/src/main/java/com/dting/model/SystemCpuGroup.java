package com.dting.model;

import java.io.Serializable;
import java.util.List;

/**
 * 一组Cpu的使用率计算
 *
 * @author huangfu
 * @date 2022年10月17日09:17:42
 */
public class SystemCpuGroup extends SystemCpuInfo implements Serializable {
    private static final long serialVersionUID = 4058102432728498026L;

    /**
     * 每一块CPU的状态信息
     */
    private List<SystemCpu> systemCpus;

    public List<SystemCpu> getSystemCpus() {
        return systemCpus;
    }

    public void setSystemCpus(List<SystemCpu> systemCpus) {
        this.systemCpus = systemCpus;
    }
}
