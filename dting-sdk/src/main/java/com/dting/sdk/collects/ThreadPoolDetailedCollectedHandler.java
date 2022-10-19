package com.dting.sdk.collects;

import cn.hutool.core.bean.BeanUtil;
import com.dting.cache.DtingThreadPoolCache;
import com.dting.common.datas.ThreadPoolInfo;
import com.dting.sdk.reactor.MessageBuffer;
import com.dting.sdk.reactor.MessageReactor;
import com.dting.show.datas.ThreadPoolDetailedMessage;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 线程池详细的注册采集器
 *
 * @author huangfu
 * @date 2022年10月19日08:53:24
 */
public class ThreadPoolDetailedCollectedHandler {

    private final static ScheduledThreadPoolExecutor COLLECTED = new ScheduledThreadPoolExecutor(1, r -> {
        Thread thread = new Thread(r);
        thread.setName("ThreadPoolDetailedCollected");
        return thread;
    });

    private final static Set<String> REGISTER_THREAD_POOL_NAME = new CopyOnWriteArraySet<>();

    /**
     * 服务启动汇报一次
     */
    public static void run() {

        COLLECTED.scheduleWithFixedDelay(() -> {
            List<ThreadPoolInfo> threadPoolInfos = DtingThreadPoolCache.threadPoolInfos();

            threadPoolInfos.forEach(info -> {
                if(REGISTER_THREAD_POOL_NAME.add(info.getPoolName())) {
                    ThreadPoolDetailedMessage threadPoolDetailedMessage = new ThreadPoolDetailedMessage();
                    BeanUtil.copyProperties(info, threadPoolDetailedMessage);
                    threadPoolDetailedMessage.setQueueTotalSize(info.getQueueUseSize() + info.getQueueRemainingCapacity());
                    MessageReactor.sendMessage(new MessageBuffer(threadPoolDetailedMessage));
                }
            });
        }, 20L, 120, TimeUnit.SECONDS);

    }

    public static void stop() {
        COLLECTED.shutdown();
    }
}
