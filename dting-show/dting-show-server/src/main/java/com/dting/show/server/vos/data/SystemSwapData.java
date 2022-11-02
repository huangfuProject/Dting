package com.dting.show.server.vos.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统交换内存数据
 *
 * @author huangfu
 * @date 2022年10月28日15:37:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemSwapData extends BaseMonitorData {
    private static final long serialVersionUID = -1853627540645249038L;


    /**
     * 已经使用的交换区大小
     */
    private long useSystemSwap;

    /**
     * 可用的最大交换区大小
     */
    private long maxSystemSwap;
}
