package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * CPU的采集数据
 *
 * @author huangfu
 * @date 2022年10月19日14:02:12
 */

@Data
@TableName("message_cpu_snapshot")
@EqualsAndHashCode(callSuper = true)
public class MessageCpuSnapshot extends DtingMessageBaseEntity{
    /**
     * 总使用比率
     */
    private Double totalUse;

    /**
     * 用户使用比率
     */
    private Double userUse;

    /**
     * 系统使用比率
     */
    private Double systemUse;

    /**
     * 等待率
     */
    private Double wait;

    /**
     * 错误率
     */
    private Double error;

    /**
     * 空闲率
     */
    private Double idle;

    /**
     * 每一个CPU的使用详细
     */
    private String cpuInfo;
}
