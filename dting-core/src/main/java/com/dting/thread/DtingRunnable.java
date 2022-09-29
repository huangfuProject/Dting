package com.dting.thread;

/**
 * 任务接口
 *
 * @author huangfu
 * @date 2022年9月29日10:03:22
 */
public abstract class DtingRunnable implements Runnable {

    /**
     * 覆盖异步任务的原本实现
     */
    @Override
    public void run() {

    }

    /**
     * 真正的业务方法
     */
    public abstract void doRun();
}
