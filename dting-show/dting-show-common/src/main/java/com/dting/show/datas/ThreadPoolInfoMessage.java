package com.dting.show.datas;

import com.dting.common.datas.ThreadPoolInfo;
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
public class ThreadPoolInfoMessage  extends DtingMessage implements Serializable {
    private static final long serialVersionUID = -239721540257804735L;

    private List<ThreadPoolInfo> threadPoolInfos;

    public ThreadPoolInfoMessage(List<ThreadPoolInfo> threadPoolInfos) {
        this.threadPoolInfos = threadPoolInfos;
    }

    public List<ThreadPoolInfo> getThreadPoolInfos() {
        return threadPoolInfos;
    }

    public void setThreadPoolInfos(List<ThreadPoolInfo> threadPoolInfos) {
        this.threadPoolInfos = threadPoolInfos;
    }
}
