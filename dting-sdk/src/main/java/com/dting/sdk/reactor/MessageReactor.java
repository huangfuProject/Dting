package com.dting.sdk.reactor;

import com.dting.Subject;
import com.dting.message.client.cache.ServerCommunicationConnectionPool;
import com.dting.message.client.config.MessageClientConfig;
import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.sdk.observers.TaskInfoObserver;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消息反应堆
 *
 * @author huangfu
 * @date 2022年10月17日13:40:01
 */
public class MessageReactor {
    /**
     * 指定RingBuffer的大小
     * 该参数必须是2的幂次方
     * 2的15次方
     */
    private static final int TASK_INFO_BUFFER_SIZE = (int) Math.pow(2, 15);

    /**
     * 发送线程的线程id
     */
    private static final AtomicInteger TASK_INFO_ID = new AtomicInteger(0);

    /**
     * 发送任务的线程工厂
     */
    private static final ThreadFactory TASK_INFO_QUEUE_THREAD_FACTORY = r -> new Thread(r, "TASK_INFO_QUEUE_THREAD_FACTORY:" + TASK_INFO_ID.incrementAndGet());


    /**
     * 阻塞策略
     */
    private static final BlockingWaitStrategy STRATEGY = new BlockingWaitStrategy();

    /**
     * RingBuffer生产工厂,初始化RingBuffer的时候使用
     * 不需要自己创建  自动创建  这个是自动创建的工厂
     */
    private static final EventFactory<MessageBuffer> FACTORY = MessageBuffer::new;

    /**
     * 发送队列
     */
    private static final Disruptor<MessageBuffer> SEND_QUEUE = new Disruptor<>(FACTORY, TASK_INFO_BUFFER_SIZE, TASK_INFO_QUEUE_THREAD_FACTORY, ProducerType.SINGLE, STRATEGY);

    /**
     * 上下文活跃状态
     */
    private static final AtomicBoolean CONTEXT_ACTIVE = new AtomicBoolean(true);


    public static void sendMessage(MessageBuffer messageBuffer) {
        if (CONTEXT_ACTIVE.get()) {
            RingBuffer<MessageBuffer> ringBuffer = SEND_QUEUE.getRingBuffer();
            // 获取下一个可用位置的下标
            long sequence = ringBuffer.next();
            MessageBuffer messageBufferModel = ringBuffer.get(sequence);
            //重新赋值数据 将该数据项内存映射中存储一份
            messageBufferModel.setDtingMessage(messageBuffer.getDtingMessage());
            ringBuffer.publish(sequence);
        } else {
            System.err.println("消息发送失败，上下文已经被关闭！");
        }
    }

    /**
     * 开始处理器
     */
    public static void start(List<MessageClientConfig> configList) {
        //追加任务执行信息观察者
        Subject.attachObserver(new TaskInfoObserver());
        //开启全部的服务
        ServerCommunicationConnectionPool.startAll(configList);
        //设置处理器
        SEND_QUEUE.handleEventsWith(new SendMessageQueueEventHandler());
        //开始接收来自各方的消息
        SEND_QUEUE.start();

    }

    /**
     * 关闭处理器
     */
    public static void stop() {
        SEND_QUEUE.shutdown();
        //将上下文状态修正为不可用
        CONTEXT_ACTIVE.compareAndSet(true, false);
        ServerCommunicationConnectionPool.closeAll();
    }

    /**
     * 待发送队列的处理器
     */
    public static class SendMessageQueueEventHandler implements EventHandler<MessageBuffer> {

        @Override
        public void onEvent(MessageBuffer messageBuffer, long l, boolean b) throws Exception {
            DtingMessage dtingMessage = messageBuffer.getDtingMessage();
            //向服务器异步发送消息
            ServerCommunicationConnectionPool.asyncAllSendMessage(dtingMessage);
        }
    }
}


