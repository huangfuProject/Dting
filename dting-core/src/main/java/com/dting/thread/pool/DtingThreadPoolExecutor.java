package com.dting.thread.pool;

import com.dting.thread.DtingRunnable;
import com.dting.thread.rejected.DtingRejectedExecutionHandler;

import java.util.concurrent.*;

/**
 * 监控线程池
 *
 * @author huangfu
 * @date 2022年9月29日09:55:40
 */
public class DtingThreadPoolExecutor extends ThreadPoolExecutor {

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
        if (r instanceof DtingRunnable) {
            DtingRunnable dtingRunnable = (DtingRunnable) r;
            //拼装线程名称   线程池名称/线程名称/任务名称
            dtingRunnable.setTaskName(String.format("%s/%s/%s", threadPoolName, t.getName(), dtingRunnable.getTaskName()));
            System.out.println(dtingRunnable.getTaskName() + ": 开始运行");
        }
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if (r instanceof DtingRunnable) {
            DtingRunnable dtingRunnable = (DtingRunnable) r;
            System.out.println(dtingRunnable.getTaskName() + ": 运行完毕");
        }
        super.afterExecute(r, t);
    }

    /**
     * 返回线程池的名称
     *
     * @return 线程池的名称
     */
    public String getThreadPoolName() {
        return threadPoolName;
    }
}
