package com.dting.show.server.websocket.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统内存监控数据
 *
 * @author huangfu
 * @date 2022年10月26日09:17:35
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SystemMemoryData extends WebSocketBaseData implements Serializable {
    private static final long serialVersionUID = -8381148704658708560L;

    /**
     * 已经使用的内存
     */
    private long systemUseMemory;

    /**
     * 可用的最大内存
     */
    private long systemMaxMemory;
}
