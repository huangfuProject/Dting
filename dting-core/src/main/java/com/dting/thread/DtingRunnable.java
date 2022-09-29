package com.dting.thread;

/**
 * 任务接口
 *
 * @author huangfu
 * @date 2022年9月29日10:03:22
 */
public class DtingRunnable implements Runnable {

    /**
     * 真正需要执行的任务
     */
    private final Runnable runnable;

    /**
     * 任务名称
     */
    private String taskName;

    public DtingRunnable(Runnable runnable, String taskName) {
        this.runnable = runnable;
        this.taskName = taskName;
    }

    /**
     * 覆盖异步任务的原本实现
     */
    @Override
    public void run() {
        doRun();
    }

    /**
     * 真正的业务方法
     */
    public void doRun() {
        runnable.run();
    }

    /**
     * 获取任务名称
     *
     * @return 返回任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称
     *
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
