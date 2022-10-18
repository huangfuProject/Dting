package com.dting.show.datas;

import com.dting.common.datas.NetInfo;
import com.dting.common.datas.SystemCpuGroup;
import com.dting.common.datas.SystemMemory;
import com.dting.common.datas.SystemPropertiesAbstract;
import com.dting.message.common.agreement.packet.DtingMessage;

import java.io.Serializable;
import java.util.List;

/**
 * 系统配置的消息
 *
 * @author huangfu
 * @date 2022年10月18日08:43:30
 */
public class SystemInfoMessage extends DtingMessage implements Serializable {
    private static final long serialVersionUID = -5890865427452389237L;
    /**
     * jvm及操作系统摘要信息
     */
    private SystemPropertiesAbstract systemPropertiesAbstract;

    /**
     * 系统内存信息
     */
    private SystemMemory systemMemory;

    /**
     * cpu信息
     */
    private SystemCpuGroup systemCpuGroup;

    /**
     * 网卡信息
     */
    private List<NetInfo> netInfos;

    public SystemPropertiesAbstract getSystemPropertiesAbstract() {
        return systemPropertiesAbstract;
    }

    public void setSystemPropertiesAbstract(SystemPropertiesAbstract systemPropertiesAbstract) {
        this.systemPropertiesAbstract = systemPropertiesAbstract;
    }

    public SystemMemory getSystemMemory() {
        return systemMemory;
    }

    public void setSystemMemory(SystemMemory systemMemory) {
        this.systemMemory = systemMemory;
    }

    public SystemCpuGroup getSystemCpuGroup() {
        return systemCpuGroup;
    }

    public void setSystemCpuGroup(SystemCpuGroup systemCpuGroup) {
        this.systemCpuGroup = systemCpuGroup;
    }

    public List<NetInfo> getNetInfos() {
        return netInfos;
    }

    public void setNetInfos(List<NetInfo> netInfos) {
        this.netInfos = netInfos;
    }

    @Override
    public String toString() {
        return "SystemInfoMessage{" +
                "systemPropertiesAbstract=" + systemPropertiesAbstract +
                ", systemMemory=" + systemMemory +
                ", systemCpuGroup=" + systemCpuGroup +
                ", netInfos=" + netInfos +
                '}';
    }
}
