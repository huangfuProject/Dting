package com.dting.show.server.vos.monitoring;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基类数据
 *
 * @author huangfu
 * @date 2022年10月28日15:35:52
 */
@Data
public class BaseMonitorData implements Serializable {
    private static final long serialVersionUID = 3551109661017209710L;
    /**
     * 时间数据,数据的采集时间格式化数据
     */
    private String dateValue;

    public void setDateValue(long dateTime) {
        this.dateValue = DateUtil.format(new Date(dateTime), "yyyy/MM/dd HH:mm:ss");
    }

}
