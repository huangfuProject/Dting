package com.dting.thread.pool;


import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DtingThreadPoolExecutorTest {

    private static final int MILLIS = 200;

    @Test
    public void threadPoolExecThread() throws InterruptedException {
        DtingThreadPoolExecutor threadPoolExecutor = new DtingThreadPoolExecutor(2,4,60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(1024),"tese-pool");

        threadPoolExecutor.execute(new DtingRunnable("test-task", () -> {
            try {
                Thread.sleep(MILLIS);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }));

        threadPoolExecutor.execute(new DtingRunnable("test-task", () -> {
            try {
                Thread.sleep(MILLIS);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }));
        Thread.sleep(MILLIS * 2);
        threadPoolExecutor.shutdown();
    }
}