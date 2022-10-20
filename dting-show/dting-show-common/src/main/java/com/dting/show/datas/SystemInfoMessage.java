package com.dting.show.datas;

import com.dting.common.datas.NetDataCollect;
import com.dting.common.datas.SystemCpuGroupCollectCollect;
import com.dting.common.datas.SystemMemoryCollect;
import com.dting.common.datas.SystemPropertiesAbstractCollect;
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
    private SystemPropertiesAbstractCollect systemPropertiesAbstractCollect;

    /**
     * 系统内存信息
     */
    private SystemMemoryCollect systemMemoryCollect;

    /**
     * cpu信息
     */
    private SystemCpuGroupCollectCollect systemCpuGroupCollect;

    /**
     * 网卡信息
     */
    private List<NetDataCollect> netDatumCollects;

    public SystemPropertiesAbstractCollect getSystemPropertiesAbstract() {
        return systemPropertiesAbstractCollect;
    }

    public void setSystemPropertiesAbstract(SystemPropertiesAbstractCollect systemPropertiesAbstractCollect) {
        this.systemPropertiesAbstractCollect = systemPropertiesAbstractCollect;
    }

    public SystemMemoryCollect getSystemMemory() {
        return systemMemoryCollect;
    }

    public void setSystemMemory(SystemMemoryCollect systemMemoryCollect) {
        this.systemMemoryCollect = systemMemoryCollect;
    }

    public SystemCpuGroupCollectCollect getSystemCpuGroup() {
        return systemCpuGroupCollect;
    }

    public void setSystemCpuGroup(SystemCpuGroupCollectCollect systemCpuGroupCollect) {
        this.systemCpuGroupCollect = systemCpuGroupCollect;
    }

    public List<NetDataCollect> getNetInfos() {
        return netDatumCollects;
    }

    public void setNetInfos(List<NetDataCollect> netDatumCollects) {
        this.netDatumCollects = netDatumCollects;
    }

    @Override
    public String toString() {
        return "SystemInfoMessage{" +
                "systemPropertiesAbstract=" + systemPropertiesAbstractCollect +
                ", systemMemory=" + systemMemoryCollect +
                ", systemCpuGroup=" + systemCpuGroupCollect +
                ", netInfos=" + netDatumCollects +
                '}';
    }
}
