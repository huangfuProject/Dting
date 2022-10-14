package com.dting.thread.pool;

import com.dting.model.TaskInfo;

import java.util.concurrent.*;

/**
 * 监控线程池
 *
 * @author huangfu
 * @date 2022年9月29日09:55:40
 */
public class DtingThreadPoolExecutor extends ThreadPoolExecutor {

    /**
     * 记录每一个任务运行时的状态
     */
    protected final static ThreadLocal<TaskInfo> TASK_INFO_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 线程池的名称
     */
    private final String threadPoolName;

    /**
     * 默认的线程名称
     */
    private final static String DEFAULT_TASK_NAME = "DEFAULT";

    public DtingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, String threadPoolName) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), new DtingRejectedExecutionHandler(new AbortPolicy()), threadPoolName);
    }

    public DtingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, String threadPoolName) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, new DtingRejectedExecutionHandler(new AbortPolicy()), threadPoolName);
    }

    public DtingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, DtingRejectedExecutionHandler handler, String threadPoolName) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), handler, threadPoolName);
    }

    public DtingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, DtingRejectedExecutionHandler handler, String threadPoolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.threadPoolName = threadPoolName;
    }

    public DtingThreadPoolExecutor(ThreadPoolExecutor oldThreadPoolExecutor, String threadPoolName) {
        this(
                oldThreadPoolExecutor.getCorePoolSize(),
                oldThreadPoolExecutor.getMaximumPoolSize(),
                oldThreadPoolExecutor.getKeepAliveTime(TimeUnit.MILLISECONDS),
                TimeUnit.MILLISECONDS,
                oldThreadPoolExecutor.getQueue(),
                oldThreadPoolExecutor.getThreadFactory(),
                new DtingRejectedExecutionHandler(oldThreadPoolExecutor.getRejectedExecutionHandler()),
                threadPoolName);
    }

    @Override
    public void execute(Runnable command) {
        //包装任务执行操作
        DtingRunnable dtingRunnable = new DtingRunnable(DEFAULT_TASK_NAME, command);
        this.execute(dtingRunnable);
    }

    public void execute(DtingRunnable command) {
        super.execute(command);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        try {
            if (r instanceof DtingRunnable) {
                DtingRunnable dtingRunnable = (DtingRunnable) r;
                //拼装线程名称   线程池名称/线程名称/任务名称
                dtingRunnable.setTaskName(String.format("%s/%s/%s", threadPoolName, t.getName(), dtingRunnable.getTaskName()));
                System.out.println(dtingRunnable.getTaskName() + ": 开始运行");
                cacheTaskInfo(dtingRunnable);
            }
        } finally {
            super.beforeExecute(t, r);
        }
    }

    /**
     * 缓存任务信息
     *
     * @param dtingRunnable 任务
     */
    private void cacheTaskInfo(DtingRunnable dtingRunnable) {
        TaskInfo taskInfo = new TaskInfo();
        //设置开始时间
        taskInfo.setStartTime(System.nanoTime());
        //设置当前线程的活跃数量
        taskInfo.setActiveCount(this.getActiveCount());
        //任务名称
        taskInfo.setTaskName(dtingRunnable.getTaskName());
        //线程池名称
        taskInfo.setThreadPoolName(threadPoolName);
        BlockingQueue<Runnable> queue = this.getQueue();
        //队列剩余
        taskInfo.setQueueRemainingCapacity(queue.remainingCapacity());
        //队列长度
        taskInfo.setQueueSize(queue.size());
        TASK_INFO_THREAD_LOCAL.set(taskInfo);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            if (r instanceof DtingRunnable) {
                DtingRunnable dtingRunnable = (DtingRunnable) r;
                System.out.println(dtingRunnable.getTaskName() + ": 运行完毕");
            }
            super.afterExecute(r, t);
        } finally {
            TaskInfo taskInfo = TASK_INFO_THREAD_LOCAL.get();
            taskInfo.setEndTime(System.nanoTime());
            //通知观察者
            taskInfo.noticeAllDtingObserver();
            //删除运行任务的缓存数据
            TASK_INFO_THREAD_LOCAL.remove();
        }
    }

    /**
     * 返回线程池的名称
     *
     * @return 线程池的名称
     */
    public String getThreadPoolName() {
        return threadPoolName;
    }

    @Override
    public RejectedExecutionHandler getRejectedExecutionHandler() {
        RejectedExecutionHandler rejectedExecutionHandler = super.getRejectedExecutionHandler();
        //如果是包装后的对象
        if (rejectedExecutionHandler instanceof DtingRejectedExecutionHandler) {
            DtingRejectedExecutionHandler targetRejectedExecutionHandler = (DtingRejectedExecutionHandler) rejectedExecutionHandler;
            //返回真实的拒绝策略
            return targetRejectedExecutionHandler.getRejectedExecutionHandler();
        }
        return rejectedExecutionHandler;
    }
}
