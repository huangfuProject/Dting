package com.dting.sdk.collects;

import java.util.concurrent.ScheduledThreadPoolExecutor;

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

    }
}
