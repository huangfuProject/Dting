package com.dting.common.datas;

import java.io.Serializable;

/**
 * 网络IO数据
 *
 * @author huangfu
 * @date 2022年10月17日11:04:06
 */
public class NetInfo implements Serializable {
    private static final long serialVersionUID = 873566552935777151L;

    /**
     * 网卡名称
     */
    private String networkName;

    /**
     * 地址
     */
    private String ipAddress;

    /**
     * 子网掩码
     */
    private String subnetMask;

    /**
     * 接收的数据包数量
     */
    private Long receivingDataPacketCount;

    /**
     * 发送的数据包数量
     */
    private Long sendDataPacketCount;

    /**
     * 接收的数据字节数
     */
    private Long receivingDataByte;

    /**
     * 发送的数据包字节数
     */
    private Long sendDataByte;


    /**
     * 接收的错误数据包数量
     */
    private Long receivingErrorDataPacketCount;

    /**
     * 发送的错误数据包数量
     */
    private Long sendErrorDataPacketCount;

    /**
     * 接收的丢弃数据包数量
     */
    private Long receivingDiscardedDataPacketCount;

    /**
     * 发送的丢弃数据包数量
     */
    private Long sendDiscardedDataPacketCount;

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public Long getReceivingDataPacketCount() {
        return receivingDataPacketCount;
    }

    public void setReceivingDataPacketCount(Long receivingDataPacketCount) {
        this.receivingDataPacketCount = receivingDataPacketCount;
    }

    public Long getSendDataPacketCount() {
        return sendDataPacketCount;
    }

    public void setSendDataPacketCount(Long sendDataPacketCount) {
        this.sendDataPacketCount = sendDataPacketCount;
    }

    public Long getReceivingDataByte() {
        return receivingDataByte;
    }

    public void setReceivingDataByte(Long receivingDataByte) {
        this.receivingDataByte = receivingDataByte;
    }

    public Long getSendDataByte() {
        return sendDataByte;
    }

    public void setSendDataByte(Long sendDataByte) {
        this.sendDataByte = sendDataByte;
    }

    public Long getReceivingErrorDataPacketCount() {
        return receivingErrorDataPacketCount;
    }

    public void setReceivingErrorDataPacketCount(Long receivingErrorDataPacketCount) {
        this.receivingErrorDataPacketCount = receivingErrorDataPacketCount;
    }

    public Long getSendErrorDataPacketCount() {
        return sendErrorDataPacketCount;
    }

    public void setSendErrorDataPacketCount(Long sendErrorDataPacketCount) {
        this.sendErrorDataPacketCount = sendErrorDataPacketCount;
    }

    public Long getReceivingDiscardedDataPacketCount() {
        return receivingDiscardedDataPacketCount;
    }

    public void setReceivingDiscardedDataPacketCount(Long receivingDiscardedDataPacketCount) {
        this.receivingDiscardedDataPacketCount = receivingDiscardedDataPacketCount;
    }

    public Long getSendDiscardedDataPacketCount() {
        return sendDiscardedDataPacketCount;
    }

    public void setSendDiscardedDataPacketCount(Long sendDiscardedDataPacketCount) {
        this.sendDiscardedDataPacketCount = sendDiscardedDataPacketCount;
    }

}
