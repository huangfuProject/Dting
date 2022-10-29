package com.dting.show.server.websocket;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.dting.show.server.conditions.MemoryBatchCondition;
import com.dting.show.server.entity.MessageMemorySnapshot;
import com.dting.show.server.service.MessageMemorySnapshotService;
import com.dting.show.server.websocket.data.SystemMemoryData;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 动态的迭代任务池子，主要用于迭代查询需要推送的websocket信息
 *
 * @author huangfu
 * @date 2022年10月26日16:54:10
 */
@Component
public class DynamicIterativeTaskPoolService implements InitializingBean, DisposableBean {

    private final MessageMemorySnapshotService messageMemorySnapshotService;
    /**
     * 动态查询池
     */
    private final static Map<String, MessageWebSocket> DYNAMIC_SELECT_POOL = new ConcurrentHashMap<>(32);

    /**
     * 保存查询的变量
     */
    private final static Map<String, MessageVariable> MESSAGE_VARIABLE_SELECT_POOL = new ConcurrentHashMap<>(32);

    private final static AtomicBoolean IS_RUN = new AtomicBoolean(true);

    /**
     * 线程池
     */
    private final static ThreadPoolExecutor DYNAMIC_THREAD = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), new ThreadFactory() {
        @Override
        public Thread newThread(@NotNull Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("DYNAMIC_THREAD");
            return thread;
        }
    }, new ThreadPoolExecutor.AbortPolicy());

    public DynamicIterativeTaskPoolService(MessageMemorySnapshotService messageMemorySnapshotService) {
        this.messageMemorySnapshotService = messageMemorySnapshotService;
    }


    /**
     * 添加一个迭代任务
     *
     * @param messageWebSocket 迭代任务
     */
    public static void addMessageWebSocket(MessageWebSocket messageWebSocket) {

        WebsocketSendData websocketSendData = messageWebSocket.getWebsocketSendData();
        DYNAMIC_SELECT_POOL.put(websocketSendData.getSessionId(), messageWebSocket);

    }

    public static void removeMessageWebSocket(MessageWebSocket messageWebSocket) {
        WebsocketSendData websocketSendData = messageWebSocket.getWebsocketSendData();
        DYNAMIC_SELECT_POOL.remove(websocketSendData.getSessionId());
        MESSAGE_VARIABLE_SELECT_POOL.remove(websocketSendData.getSessionId());
    }

    @Override
    public void destroy() throws Exception {
        IS_RUN.compareAndSet(true, false);
        DYNAMIC_THREAD.shutdown();

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            DYNAMIC_THREAD.execute(() -> {
                try {
                    while (IS_RUN.get() && !Thread.currentThread().isInterrupted()) {
                        DYNAMIC_SELECT_POOL.forEach((k, v) -> {
                            WebsocketSendData websocketSendData = v.getWebsocketSendData();
                            //处理这个消息任务
                            doHandler(v, websocketSendData);
                        });
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理器
     *
     * @param webSocket         websocket服务
     * @param websocketSendData 消息
     */
    private void doHandler(MessageWebSocket webSocket, WebsocketSendData websocketSendData) {
        String sessionId = websocketSendData.getSessionId();
        //获取一个迭代对象
        MessageVariable messageVariable = MESSAGE_VARIABLE_SELECT_POOL.getOrDefault(sessionId, new MessageVariable());
        //内存数据查询
        List<MessageMemorySnapshot> messageMemorySnapshots = memoryDataFind(websocketSendData, messageVariable);
        if (CollectionUtil.isEmpty(messageMemorySnapshots)) {
            return;
        }
        memoryDataHandler(webSocket, messageMemorySnapshots);
        MessageMemorySnapshot messageMemorySnapshot = messageMemorySnapshots.get(messageMemorySnapshots.size() - 1);
        messageVariable.setThisMemoryStartTime(messageMemorySnapshot.getCollectTime());
        //缓存迭代对象
        MESSAGE_VARIABLE_SELECT_POOL.put(sessionId, messageVariable);

    }

    /**
     * 内存数据关联websocket查询
     *
     * @param webSocket              客户端
     * @param messageMemorySnapshots 内存数据
     */
    private void memoryDataHandler(MessageWebSocket webSocket, List<MessageMemorySnapshot> messageMemorySnapshots) {
        if (CollectionUtil.isNotEmpty(messageMemorySnapshots)) {
            messageMemorySnapshots.forEach(messageMemorySnapshot -> {
                SystemMemoryData systemMemoryData = new SystemMemoryData();
                systemMemoryData.setDateValue(messageMemorySnapshot.getCollectTime());
                systemMemoryData.setSystemMaxMemory(messageMemorySnapshot.getTotalMemory()/1024/1024);
                systemMemoryData.setSystemUseMemory(messageMemorySnapshot.getUseMemory()/1024/1024);
                webSocket.sendMessage(JSON.toJSONString(systemMemoryData));
            });
        }
    }

    /**
     * 内存迭代数据查询
     *
     * @param websocketSendData websocket发送过来的查询条件
     * @param messageVariable   当前环境的迭代对象
     * @return 查询的结果
     */
    private List<MessageMemorySnapshot> memoryDataFind(WebsocketSendData websocketSendData, MessageVariable messageVariable) {
        //构建查询条件
        MemoryBatchCondition memoryBatchCondition = new MemoryBatchCondition();
        //查询服务环境
        memoryBatchCondition.setServerEnv(websocketSendData.getServerEnv());
        //查询服务主键
        memoryBatchCondition.setServerKey(websocketSendData.getServerKey());
        //查询实例主键
        memoryBatchCondition.setInstanceKey(websocketSendData.getInstanceKey());
        //设置结束时间
        long endTime = websocketSendData.getEndTime();
        //终止条件  当前 结束时间大于0 且存在迭代条件开始时间不为空的情况下，这里终止查询 返回一个空数组
        if (endTime > 0 && messageVariable.getThisMemoryStartTime() != null) {
            return new ArrayList<>();
        }

        if (endTime < 0) {
            memoryBatchCondition.setEndTime(System.currentTimeMillis());
        } else {
            memoryBatchCondition.setEndTime(endTime);
        }
        //设置开始时间
        if (messageVariable.getThisMemoryStartTime() != null) {
            //取迭代条件中的开始时间
            memoryBatchCondition.setStartTime(messageVariable.getThisMemoryStartTime());
        } else {
            //取设置的开始时间
            memoryBatchCondition.setStartTime(websocketSendData.getStartTime());
        }

        return messageMemorySnapshotService.memoryBatchFindByCondition(memoryBatchCondition);
    }

    /**
     * message变量
     */
    @Data
    private static class MessageVariable {

        /**
         * 当前内存查询迭代的id
         */
        private Long thisMemoryStartTime;
    }
}
