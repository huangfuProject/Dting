package com.dting.show.server.vos.monitoring;

import com.dting.show.server.vos.data.SystemCpuData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * *************************************************<br/>
 * CPU前端映射数据<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/2 21:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemCpuDataVo extends BaseMonitorVo {
    private static final long serialVersionUID = -6732885833804509790L;

    /**
     * 一个时间段范围的CPU数据
     */
    private List<SystemCpuData> systemCpuDataList;
}
