package com.dting.sdk.collects;

import com.dting.cache.DtingThreadPoolCache;
import com.dting.common.datas.ThreadPoolInfo;
import com.dting.sdk.reactor.MessageBuffer;
import com.dting.sdk.reactor.MessageReactor;
import com.dting.show.datas.ThreadPoolInfoMessage;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池相关的采集器
 *
 * @author huangfu
 * @date 2022年10月18日17:39:16
 */
public class DtingThreadPoolCollectedHandler {
    private final static ScheduledThreadPoolExecutor COLLECTED = new ScheduledThreadPoolExecutor(1, r -> {
        Thread thread = new Thread(r);
        thread.setName("SystemMetricsCollected");
        return thread;
    });


    /**
     * 开始运行
     */
    public static void run(){
        COLLECTED.scheduleWithFixedDelay(() -> {
            List<ThreadPoolInfo> threadPoolInfos = DtingThreadPoolCache.threadPoolInfos();
            ThreadPoolInfoMessage threadPoolInfoMessage = new ThreadPoolInfoMessage(threadPoolInfos);
            MessageBuffer messageBuffer = new MessageBuffer(threadPoolInfoMessage);
            MessageReactor.sendMessage(messageBuffer);
        }, 10L, 25, TimeUnit.SECONDS);

    }

    public static void stop() {
        COLLECTED.shutdown();
    }
}
