package com.dting.show.server.buffers;

import io.netty.util.internal.shaded.org.jctools.queues.MpscUnboundedArrayQueue;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 消息缓冲反应堆
 *
 * @author huangfu
 * @date 2022年10月19日16:31:35
 */
public abstract class MessageBufferReactor<T> implements InitializingBean, DisposableBean {

    private final ScheduledThreadPoolExecutor REACTOR_EXECUTOR = new ScheduledThreadPoolExecutor(1, r -> {
        Thread thread = new Thread(r);
        thread.setName(this.getClass().getName());
        return thread;
    });

    /**
     * 反映容器
     */
    private final MpscUnboundedArrayQueue<T> REACTION_VESSEL = new MpscUnboundedArrayQueue<>(4096);

    public void start() {
        REACTOR_EXECUTOR.scheduleWithFixedDelay(() -> {
            //获取线程内堆积的数据量
            int size = REACTION_VESSEL.size();
            if (size > 0) {
                List<T> cache = new ArrayList<>();
                REACTION_VESSEL.drain(cache::add);
                start0(cache);
            }

        }, 5L, 5L, TimeUnit.SECONDS);
    }

    public void stop() {
        REACTOR_EXECUTOR.shutdown();
    }

    /**
     * 开始任务
     *
     * @param source 要处理的数据
     */
    public abstract void start0(List<T> source);

    /**
     * 添加原料
     *
     * @param material 原材料
     */
    public void addMaterial(T material) {
        REACTION_VESSEL.offer(material);
    }

    @Override
    public void destroy() throws Exception {
        this.stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }
}
