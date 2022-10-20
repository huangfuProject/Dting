package com.dting.thread.pool;

import com.dting.common.datas.TaskLogCollect;
import com.dting.model.TaskInfoSubject;
import com.dting.utils.DtingLogUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 内部封装的拒绝策略，模板方法，埋点拒绝方法的执行
 *
 * @author huangfu
 * @date 2022年9月29日10:33:58
 */
class DtingRejectedExecutionHandler implements RejectedExecutionHandler {

    private final RejectedExecutionHandler rejectedExecutionHandler;
    private final DtingThreadPoolExecutor dtingThreadPoolExecutor;

    private final AtomicLong REJECTED_COUNT = new AtomicLong(0);


    protected DtingRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler, DtingThreadPoolExecutor dtingThreadPoolExecutor) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
        this.dtingThreadPoolExecutor = dtingThreadPoolExecutor;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        TaskLogCollect taskLogCollect = new TaskLogCollect();
        taskLogCollect.setStartTime(System.nanoTime());
        taskLogCollect.setThreadPoolName(dtingThreadPoolExecutor.threadPoolName);

        //设置当前线程的活跃数量
        taskLogCollect.setActiveCount(dtingThreadPoolExecutor.getActiveCount());
        //任务名称
        if (r instanceof DtingRunnable) {
            taskLogCollect.setTaskName(((DtingRunnable) r).getTaskName());
        }

        //线程池名称
        BlockingQueue<Runnable> queue = dtingThreadPoolExecutor.getQueue();
        //队列剩余
        taskLogCollect.setQueueRemainingCapacity(queue.remainingCapacity());
        //队列长度
        taskLogCollect.setQueueSize(queue.size());


        try {
            rejectedExecutionHandler.rejectedExecution(r, executor);
        } catch (Throwable e) {
            taskLogCollect.setErrorMsg(DtingLogUtil.messageRead(e, false));
            throw e;
        } finally {
            if (r instanceof DtingRunnable) {
                DtingRunnable target = (DtingRunnable) r;
                taskLogCollect.setTaskName(target.getTaskName());
            }

            taskLogCollect.setRejected(true);
            taskLogCollect.setSuccess(false);
            taskLogCollect.setEndTime(System.nanoTime());
            TaskInfoSubject taskInfoSubject = new TaskInfoSubject(taskLogCollect);
            //通知观察者
            taskInfoSubject.noticeAllDtingObserver();
            REJECTED_COUNT.incrementAndGet();
        }
    }

    /**
     * 拒绝策略的执行次数
     * @return 拒绝策略的执行次数
     */
    public long getRejectedCount(){
        return REJECTED_COUNT.get();
    }

    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }
}
