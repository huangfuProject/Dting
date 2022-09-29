package com.dting.thread.pool;

import com.dting.thread.rejected.DtingRejectedExecutionHandler;

import java.util.concurrent.*;

/**
 * 监控线程池
 *
 * @author huangfu
 * @date 2022年9月29日09:55:40
 */
public class DtingThreadPoolExecutor extends ThreadPoolExecutor {
    public DtingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), new DtingRejectedExecutionHandler(new AbortPolicy()));
    }

    public DtingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, new DtingRejectedExecutionHandler(new AbortPolicy()));
    }

    public DtingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, DtingRejectedExecutionHandler handler) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), handler);
    }

    public DtingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, DtingRejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public DtingThreadPoolExecutor(ThreadPoolExecutor oldThreadPoolExecutor) {
        this(
                oldThreadPoolExecutor.getCorePoolSize(),
                oldThreadPoolExecutor.getMaximumPoolSize(),
                oldThreadPoolExecutor.getKeepAliveTime(TimeUnit.MILLISECONDS),
                TimeUnit.MILLISECONDS,
                oldThreadPoolExecutor.getQueue(),
                oldThreadPoolExecutor.getThreadFactory(),
                new DtingRejectedExecutionHandler(oldThreadPoolExecutor.getRejectedExecutionHandler())
        );
    }

    @Override
    public void execute(Runnable command) {
        super.execute(command);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }
}
