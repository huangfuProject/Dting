package com.dting.show.datas;

import com.dting.common.datas.ThreadPoolDataCollect;
import com.dting.message.common.agreement.packet.DtingMessage;

import java.io.Serializable;
import java.util.List;

/**
 * *************************************************<br/>
 * 消息的统计信息<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/18 21:10
 */
public class ThreadPoolDataCollectMessage extends DtingMessage implements Serializable {
    private static final long serialVersionUID = -239721540257804735L;

    private List<ThreadPoolDataCollect> threadPoolDatumCollects;

    public ThreadPoolDataCollectMessage(List<ThreadPoolDataCollect> threadPoolDatumCollects) {
        this.threadPoolDatumCollects = threadPoolDatumCollects;
    }

    public List<ThreadPoolDataCollect> getThreadPoolDataCollect() {
        return threadPoolDatumCollects;
    }

    public void setThreadPoolDataCollect(List<ThreadPoolDataCollect> threadPoolDatumCollects) {
        this.threadPoolDatumCollects = threadPoolDatumCollects;
    }
}
