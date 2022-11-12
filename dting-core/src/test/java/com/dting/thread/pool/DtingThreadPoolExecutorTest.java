package com.dting.thread.pool;


import com.dting.subject.DtingObserver;
import com.dting.subject.Subject;
import com.dting.model.TaskInfoSubject;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DtingThreadPoolExecutorTest {

    private static final int MILLIS = 200;

    @Test
    public void threadPoolExecThread() throws InterruptedException {
        Subject.attachObserver(new TestDtingObserver());
        DtingThreadPoolExecutor threadPoolExecutor = new DtingThreadPoolExecutor(1,1,60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(10),"tese-pool");

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


        threadPoolExecutor.execute(new DtingRunnable("test-task", () -> {
            try {
                Thread.sleep(MILLIS);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }));
        Thread.sleep(MILLIS * 10);
        threadPoolExecutor.shutdown();
    }
}

class TestDtingObserver extends DtingObserver<TaskInfoSubject> {
    @Override
    public void doUpdate(TaskInfoSubject data) {
        System.out.println(data);
    }
}