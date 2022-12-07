package com.dting.sdk.test;

import com.dting.message.client.config.MessageClientConfig;
import com.dting.sdk.processing.ThreadConfigUpdateProcessing;
import com.dting.sdk.reactor.MessageReactor;
import com.dting.thread.pool.DtingRunnable;
import com.dting.thread.pool.DtingThreadPoolExecutor;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 线程池任务采集测试
 *
 * @author huangfu
 * @date 2022年10月17日15:13:27
 */
public class SdkTestExecutor {
    private static final int MILLIS = 200000;

    @Test
    public void threadPoolExecThread() throws InterruptedException {
        MessageClientConfig config = new MessageClientConfig("127.0.0.1", 8888);

        config.setServerEnv("test");
        config.setServerKey("test-Server-test");
        config.setInstanceKey("test-server-001");
        config.setTimeout(TimeUnit.HOURS.toMillis(5));
        config.addClientBusinessProcessing(new ThreadConfigUpdateProcessing());
        MessageReactor.start(Collections.singletonList(config));

        DtingThreadPoolExecutor threadPoolExecutor = new DtingThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), "tese-pool");

        threadPoolExecutor.execute(new DtingRunnable("test-task", () -> {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }));

        threadPoolExecutor.execute(new DtingRunnable("test-task", () -> {
            try {
                while (true) {
                    Thread.sleep(2000);
                    System.out.println(threadPoolExecutor.getMaximumPoolSize());
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }));

        threadPoolExecutor.execute(new DtingRunnable("test-task", () -> {
            System.out.println("123");
        }));


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
        Thread.sleep(MILLIS * 1000);
        threadPoolExecutor.shutdown();
    }
}
