package com.dting.thread.pool;

import com.dting.model.TaskInfo;
import com.dting.utils.DtingLogUtil;

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

    public DtingRunnable(String taskName, Runnable runnable) {
        this.runnable = runnable;
        this.taskName = taskName;
    }

    /**
     * 覆盖异步任务的原本实现
     */
    @Override
    public void run() {
        try {
            doRun();
        }catch (Throwable e) {
            TaskInfo taskInfo = DtingThreadPoolExecutor.TASK_INFO_THREAD_LOCAL.get();
            taskInfo.setSuccess(false);
            taskInfo.setErrorMsg(DtingLogUtil.messageRead(e, false));
            throw e;
        }
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
