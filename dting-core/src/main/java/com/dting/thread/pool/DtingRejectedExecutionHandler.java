package com.dting.thread.pool;

import com.dting.common.datas.TaskInfo;
import com.dting.model.TaskInfoSubject;
import com.dting.utils.DtingLogUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 内部封装的拒绝策略，模板方法，埋点拒绝方法的执行
 *
 * @author huangfu
 * @date 2022年9月29日10:33:58
 */
class DtingRejectedExecutionHandler implements RejectedExecutionHandler {

    private final RejectedExecutionHandler rejectedExecutionHandler;
    private final DtingThreadPoolExecutor dtingThreadPoolExecutor;


    protected DtingRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler, DtingThreadPoolExecutor dtingThreadPoolExecutor) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
        this.dtingThreadPoolExecutor = dtingThreadPoolExecutor;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setStartTime(System.nanoTime());
        taskInfo.setThreadPoolName(dtingThreadPoolExecutor.threadPoolName);

        //设置当前线程的活跃数量
        taskInfo.setActiveCount(dtingThreadPoolExecutor.getActiveCount());
        //任务名称
        if (r instanceof DtingRunnable) {
            taskInfo.setTaskName(((DtingRunnable) r).getTaskName());
        }

        //线程池名称
        BlockingQueue<Runnable> queue = dtingThreadPoolExecutor.getQueue();
        //队列剩余
        taskInfo.setQueueRemainingCapacity(queue.remainingCapacity());
        //队列长度
        taskInfo.setQueueSize(queue.size());


        try {
            rejectedExecutionHandler.rejectedExecution(r, executor);
        } catch (Throwable e) {
            taskInfo.setErrorMsg(DtingLogUtil.messageRead(e, false));
            throw e;
        } finally {
            if (r instanceof DtingRunnable) {
                DtingRunnable target = (DtingRunnable) r;
                taskInfo.setTaskName(target.getTaskName());
            }

            taskInfo.setRejected(true);
            taskInfo.setSuccess(false);
            taskInfo.setEndTime(System.nanoTime());
            TaskInfoSubject taskInfoSubject = new TaskInfoSubject(taskInfo);
            //通知观察者
            taskInfoSubject.noticeAllDtingObserver();
        }
    }

    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }
}
