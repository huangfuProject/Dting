package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统及JVM内存使用详情
 *
 * @author huangfu
 * @date 2022年10月20日08:10:06
 */
@Data
@TableName("message_memory_snapshot")
@EqualsAndHashCode(callSuper = true)
public class MessageMemorySnapshot extends DtingMessageBaseEntity implements Serializable {
    private static final long serialVersionUID = 6406529724803293622L;

    /**
     * 系统内存的总内存
     */
    private Long totalMemory;
    /**
     * 系统内存的使用内存
     */
    private Long useMemory;

    /**
     * jvm总内存
     */
    private Long jvmTotalMemory;

    /**
     * jvm已经使用的内存
     */
    private Long jvmUseMemory;

    /**
     * 交换区总内存
     */
    private Long totalSwap;

    /**
     * 交换区已经使用的大小
     */
    private Long useSwap;
}
