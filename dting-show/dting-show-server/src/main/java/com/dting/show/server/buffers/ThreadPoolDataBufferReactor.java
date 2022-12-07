package com.dting.show.server.buffers;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.dting.common.datas.ThreadPoolDataCollect;
import com.dting.show.datas.ThreadPoolDataCollectMessage;
import com.dting.show.server.entity.MessageThreadPoolSnapshot;
import com.dting.show.server.entity.ThreadPoolDetailedSnapshot;
import com.dting.show.server.service.MessageThreadPoolSnapshotService;
import com.dting.show.server.service.ThreadPoolDetailedSnapshotService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 线程池快照反应池
 *
 * @author huangfu
 * @date 2022年10月20日16:09:13
 */
public class ThreadPoolDataBufferReactor extends MessageBufferReactor<ThreadPoolDataCollectMessage> {

    private final ThreadPoolDetailedSnapshotService threadPoolDetailedSnapshotService;

    private final MessageThreadPoolSnapshotService messageThreadPoolSnapshotService;

    public ThreadPoolDataBufferReactor(ThreadPoolDetailedSnapshotService threadPoolDetailedSnapshotService, MessageThreadPoolSnapshotService messageThreadPoolSnapshotService) {
        this.threadPoolDetailedSnapshotService = threadPoolDetailedSnapshotService;
        this.messageThreadPoolSnapshotService = messageThreadPoolSnapshotService;
    }

    @Override
    public void start0(List<ThreadPoolDataCollectMessage> threadPoolDataCollectMessagesList) {
        if (CollectionUtil.isNotEmpty(threadPoolDataCollectMessagesList)) {
            List<MessageThreadPoolSnapshot> messageThreadPoolSnapshotList = new ArrayList<>(32);
            List<ThreadPoolDetailedSnapshot> threadPoolDetailedSnapshots = new ArrayList<>(32);

            threadPoolDataCollectMessagesList.forEach(threadPoolDataCollectMessage -> {
                MessageThreadPoolSnapshot messageThreadPoolSnapshot = new MessageThreadPoolSnapshot();
                //公共信息设置
                messageThreadPoolSnapshot.commonDataSetting(threadPoolDataCollectMessage);
                String collectKey = IdUtil.simpleUUID();
                messageThreadPoolSnapshot.setCollectKey(collectKey);
                messageThreadPoolSnapshotList.add(messageThreadPoolSnapshot);
                //获取每个线程池的详细信息
                List<ThreadPoolDataCollect> threadPoolDataCollects = threadPoolDataCollectMessage.getThreadPoolDataCollect();
                if (CollectionUtil.isNotEmpty(threadPoolDataCollects)) {
                    List<ThreadPoolDetailedSnapshot> threadPoolDetailedSnapshotTmp = threadPoolDataCollects.stream().map(threadPoolDataCollect -> {
                        ThreadPoolDetailedSnapshot threadPoolDetailedSnapshot = new ThreadPoolDetailedSnapshot();
                        threadPoolDetailedSnapshot.setCollectKey(collectKey);
                        threadPoolDetailedSnapshot.setThreadPoolName(threadPoolDataCollect.getPoolName());
                        threadPoolDetailedSnapshot.setActiveCount(threadPoolDataCollect.getActiveSize());
                        threadPoolDetailedSnapshot.setCoreCount(threadPoolDataCollect.getCoreSize());
                        threadPoolDetailedSnapshot.setMaxCount(threadPoolDataCollect.getMaxSize());
                        threadPoolDetailedSnapshot.setQueueUseCount(threadPoolDataCollect.getQueueUseSize());
                        threadPoolDetailedSnapshot.setQueueTotalSize(threadPoolDataCollect.getQueueUseSize() + threadPoolDataCollect.getQueueRemainingCapacity());
                        //计算繁忙程度 ((活跃数/核心数) + (活跃数/最大线程数) + (队列堆积数/队列总长度))*1000
                        double value = ((double) threadPoolDataCollect.getActiveSize()/ threadPoolDataCollect.getCoreSize() + ((double)threadPoolDataCollect.getActiveSize()/ threadPoolDataCollect.getMaxSize()) + ((double)threadPoolDataCollect.getQueueUseSize()/threadPoolDetailedSnapshot.getQueueTotalSize())) * 1000;
                        if(Double.isNaN(value)) {
                            value = 0;
                        }
                        threadPoolDetailedSnapshot.setBusyWeight(value);
                        return threadPoolDetailedSnapshot;
                    }).collect(Collectors.toList());

                    threadPoolDetailedSnapshots.addAll(threadPoolDetailedSnapshotTmp);
                }
            });

            //保存快照
            messageThreadPoolSnapshotService.batchSave(messageThreadPoolSnapshotList);
            //保存快照的详细信息
            threadPoolDetailedSnapshotService.batchSave(threadPoolDetailedSnapshots);
        }
    }
}
