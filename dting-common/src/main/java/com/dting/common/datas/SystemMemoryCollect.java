package com.dting.common.datas;

import java.io.Serializable;

/**
 * 操作系统内存用量
 *
 * @author huangfu
 * @date 2022年10月17日09:05:01
 */
public class SystemMemoryCollect implements Serializable {
    private static final long serialVersionUID = 7514715709174386666L;

    /**
     * 内存总量
     */
    private Long totalMemory;

    /**
     * 使用的内存总量
     */
    private Long useMemory;

    /**
     * 剩余的内存总量
     */
    private Long remainingMemory;

    /**
     * 交换内存的总量
     */
    private Long totalSwap;

    /**
     * 使用的交换区空间
     */
    private Long useSwap;

    /**
     * 剩余的交换区空间
     */
    private Long remainingSwap;

    public Long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public Long getUseMemory() {
        return useMemory;
    }

    public void setUseMemory(Long useMemory) {
        this.useMemory = useMemory;
    }

    public Long getRemainingMemory() {
        return remainingMemory;
    }

    public void setRemainingMemory(Long remainingMemory) {
        this.remainingMemory = remainingMemory;
    }

    public Long getTotalSwap() {
        return totalSwap;
    }

    public void setTotalSwap(Long totalSwap) {
        this.totalSwap = totalSwap;
    }

    public Long getUseSwap() {
        return useSwap;
    }

    public void setUseSwap(Long useSwap) {
        this.useSwap = useSwap;
    }

    public Long getRemainingSwap() {
        return remainingSwap;
    }

    public void setRemainingSwap(Long remainingSwap) {
        this.remainingSwap = remainingSwap;
    }
}
