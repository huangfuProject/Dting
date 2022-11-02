package com.dting.show.server.vos.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * jvm内存数据
 *
 * @author huangfu
 * @date 2022年10月28日15:37:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JvmMemoryData extends BaseMonitorData {
    private static final long serialVersionUID = -1853627540645249038L;


    /**
     * 已经使用的内存
     */
    private long useJvmMemory;

    /**
     * 可用的最大内存
     */
    private long maxJvmMemory;
}
