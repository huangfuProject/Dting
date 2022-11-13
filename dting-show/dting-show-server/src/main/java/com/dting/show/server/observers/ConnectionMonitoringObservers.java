package com.dting.show.server.observers;

import com.dting.message.server.subjects.ConnectionMonitoringSubject;
import com.dting.show.server.buffers.ConnectionMonitoringMessageBufferReactor;
import com.dting.show.server.utils.SpringUtil;
import com.dting.subject.DtingObserver;

/**
 * *************************************************<br/>
 * 链接信息监视者<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/12 16:32
 */
public class ConnectionMonitoringObservers extends DtingObserver<ConnectionMonitoringSubject> {
    @Override
    public void doUpdate(ConnectionMonitoringSubject data) {
        ConnectionMonitoringMessageBufferReactor messageBufferReactor = SpringUtil.getBean(ConnectionMonitoringMessageBufferReactor.class);
        messageBufferReactor.addMaterial(data);
    }
}
