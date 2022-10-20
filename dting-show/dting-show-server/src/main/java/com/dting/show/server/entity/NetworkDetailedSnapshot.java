package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 网卡的具体读写字节数
 *
 * @author huangfu
 * @date 2022年10月20日13:42:11
 */
@Data
@TableName("network_detailed_snapshot")
public class NetworkDetailedSnapshot {

    /**
     * 主键
     */
    @TableId
    private Integer id;

    /**
     * 关联字段
     */
    private String networkDataKey;

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
}
