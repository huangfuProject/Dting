package com.dting.show.server.vos.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * *************************************************<br/>
 * 系统级cpu使用度<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/2 21:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemCpuData  extends BaseMonitorData {
    private static final long serialVersionUID = 3961472152445688526L;
    /**
     * 总使用比率
     */
    private Double totalUse;

    /**
     * 用户使用比率
     */
    private Double userUes;

    /**
     * 系统使用比率
     */
    private Double systemUes;

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

}
