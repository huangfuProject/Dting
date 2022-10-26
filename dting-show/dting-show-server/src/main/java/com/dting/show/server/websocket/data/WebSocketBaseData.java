package com.dting.show.server.websocket.data;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * 基类数据
 *
 * @author huangfu
 * @date 2022年10月26日09:18:06
 */
@Data
public class WebSocketBaseData {

    /**
     * 时间数据
     */
    private String dateValue;

    public void setDateValue(long dateTime) {
        this.dateValue = DateUtil.format(new Date(dateTime), "yyyy/MM/dd HH:mm");
    }
}
