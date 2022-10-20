package com.dting.common.datas;

import java.io.Serializable;
import java.util.List;

/**
 * 一组Cpu的使用率计算
 *
 * @author huangfu
 * @date 2022年10月17日09:17:42
 */
public class SystemCpuGroupCollectCollect extends SystemCpuInfoCollect implements Serializable {
    private static final long serialVersionUID = 4058102432728498026L;

    /**
     * 每一块CPU的状态信息
     */
    private List<SystemCpuCollectCollect> systemCpusCollects;

    public List<SystemCpuCollectCollect> getSystemCpus() {
        return systemCpusCollects;
    }

    public void setSystemCpus(List<SystemCpuCollectCollect> systemCpusCollects) {
        this.systemCpusCollects = systemCpusCollects;
    }

}
