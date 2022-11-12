package com.dting.show.server.observers;

import com.alibaba.fastjson.JSON;
import com.dting.message.server.subjects.ConnectionMonitoringSubject;
import com.dting.subject.DtingObserver;

/**
 * *************************************************<br/>
 * s<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/12 16:32
 */
public class ConnectionMonitoringObservers extends DtingObserver<ConnectionMonitoringSubject> {
    @Override
    public void doUpdate(ConnectionMonitoringSubject data) {
        System.out.println("监听者监听到服务的链接：" + JSON.toJSONString(data));
    }
}
