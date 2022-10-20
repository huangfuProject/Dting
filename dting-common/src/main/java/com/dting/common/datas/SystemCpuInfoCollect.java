package com.dting.common.datas;

import java.io.Serializable;

/**
 * CPU的状态信息
 *
 * @author huangfu
 * @date 2022年10月17日10:05:19
 */
public class SystemCpuInfoCollect implements Serializable {
    private static final long serialVersionUID = -659570788791137679L;

    /**
     * cpu用户使用率
     */
    private Double cpuUserUse;

    /**
     * cpu系统使用率
     */
    private Double cpuSystemUse;

    /**
     * cpu等待率
     */
    private Double cpuWait;

    /**
     * cpu错误率
     */
    private Double cpuError;

    /**
     * cpu空闲率
     */
    private Double cpuIdle;

    /**
     * cpu的总使用率
     */
    private Double cpuTotal;

    public void setCpuUserUse(Double cpuUserUse) {
        this.cpuUserUse = cpuUserUse;
    }

    public void setCpuSystemUse(Double cpuSystemUse) {
        this.cpuSystemUse = cpuSystemUse;
    }

    public void setCpuWait(Double cpuWait) {
        this.cpuWait = cpuWait;
    }

    public void setCpuError(Double cpuError) {
        this.cpuError = cpuError;
    }

    public void setCpuIdle(Double cpuIdle) {
        this.cpuIdle = cpuIdle;
    }

    public void setCpuTotal(Double cpuTotal) {
        this.cpuTotal = cpuTotal;
    }

    public Double getCpuUserUse() {
        return cpuUserUse;
    }

    public Double getCpuSystemUse() {
        return cpuSystemUse;
    }


    public Double getCpuWait() {
        return cpuWait;
    }


    public Double getCpuError() {
        return cpuError;
    }


    public Double getCpuIdle() {
        return cpuIdle;
    }


    public Double getCpuTotal() {
        return cpuTotal;
    }


}
