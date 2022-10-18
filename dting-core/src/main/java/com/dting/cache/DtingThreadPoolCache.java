package com.dting.cache;

import com.dting.common.datas.ThreadPoolInfo;
import com.dting.thread.pool.DtingThreadPoolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * dting线程池的缓存池
 *
 * @author huangfu
 * @date 2022年10月18日17:44:13
 */
public class DtingThreadPoolCache {

    /**
     * 线程池缓存
     */
    private final static Map<String, DtingThreadPoolExecutor> THREAD_POOL_EXECUTOR_MAP = new ConcurrentHashMap<>(32);

    /**
     * 注册线程池
     *
     * @param dtingThreadPoolExecutor 待注册的线程池
     */
    public static void register(DtingThreadPoolExecutor dtingThreadPoolExecutor) {
        String threadPoolName = dtingThreadPoolExecutor.getThreadPoolName();
        THREAD_POOL_EXECUTOR_MAP.put(threadPoolName, dtingThreadPoolExecutor);
    }

    /**
     * 注册线程池
     *
     * @param threadPoolName 待取消注册的的线程池
     */
    public static void unRegister(String threadPoolName) {
        THREAD_POOL_EXECUTOR_MAP.remove(threadPoolName);
    }

    /**
     * 获取对应的线程池
     *
     * @param threadPoolName 线程池名称
     * @return 返回对应的线程池
     */
    public static DtingThreadPoolExecutor getThreadPool(String threadPoolName) {
        return THREAD_POOL_EXECUTOR_MAP.get(threadPoolName);
    }

    /**
     * 运行信息采集
     *
     * @return 线程池信息
     */
    public static List<ThreadPoolInfo> threadPoolInfos() {
        List<ThreadPoolInfo> threadPoolInfos = new ArrayList<>(32);

        THREAD_POOL_EXECUTOR_MAP.forEach((threadPoolName, threadPool) -> {
            ThreadPoolInfo threadPoolInfo = new ThreadPoolInfo();
            threadPoolInfo.setPoolName(threadPool.getThreadPoolName());
            threadPoolInfo.setActiveSize(threadPool.getActiveCount());
            threadPoolInfo.setCoreSize(threadPool.getCorePoolSize());
            threadPoolInfo.setMaxSize(threadPool.getMaximumPoolSize());
            BlockingQueue<Runnable> queue = threadPool.getQueue();
            threadPoolInfo.setQueueTypeName(queue.getClass().getName());
            threadPoolInfo.setQueueUseSize(queue.size());
            threadPoolInfo.setQueueRemainingCapacity(queue.remainingCapacity());
            threadPoolInfo.setCurrentRunningCompletedTaskCount(threadPool.getCompletedTaskCount());
            threadPoolInfo.setCurrentRunningRejectCount(threadPool.getRejectedTaskCount());
            threadPoolInfo.setRejectHandlerName(threadPool.getRejectedExecutionHandler().getClass().getName());
            threadPoolInfos.add(threadPoolInfo);
        });
        return threadPoolInfos;
    }
}
