package com.dting.show.server.buffers;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.common.datas.TaskLogCollect;
import com.dting.show.datas.TaskInfoMessage;
import com.dting.show.server.entity.MessageTaskRunSnapshot;
import com.dting.show.server.service.MessageTaskRunSnapshotService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务执行日志数据反应堆
 *
 * @author huangfu
 * @date 2022年10月20日11:21:38
 */
public class TaskRunLogDataBufferReactor extends MessageBufferReactor<TaskInfoMessage> {

    private final MessageTaskRunSnapshotService messageTaskRunSnapshotService;

    public TaskRunLogDataBufferReactor(MessageTaskRunSnapshotService messageTaskRunSnapshotService) {
        this.messageTaskRunSnapshotService = messageTaskRunSnapshotService;
    }

    @Override
    public void start0(List<TaskInfoMessage> sources) {
        if (CollectionUtil.isNotEmpty(sources)) {
            List<MessageTaskRunSnapshot> taskRunLogData = sources.stream().map(source -> {
                MessageTaskRunSnapshot messageTaskRunSnapshot = new MessageTaskRunSnapshot();
                messageTaskRunSnapshot.commonDataSetting(source);
                TaskLogCollect taskLogCollect = source.getTaskInfo();
                //人物名
                messageTaskRunSnapshot.setThreadPoolName(taskLogCollect.getThreadPoolName());
                // 执行组
                messageTaskRunSnapshot.setThreadPoolGroupName(String.format("%s:%s:%s:%s", source.getServerEnv(),source.getServerKey(),source.getInstanceKey(), taskLogCollect.getThreadPoolName()));
                //任务数量
                messageTaskRunSnapshot.setTaskName(taskLogCollect.getTaskName());
                //开始时间
                messageTaskRunSnapshot.setStartTime(taskLogCollect.getStartTime());
                //结束数量
                messageTaskRunSnapshot.setEndTime(taskLogCollect.getEndTime());
                //是否成功
                messageTaskRunSnapshot.setSuccess(taskLogCollect.getSuccess() ? "0" : "1");
                //是否被拒绝
                messageTaskRunSnapshot.setRejected(taskLogCollect.getRejected() ? "1" : "0");
                //失败信息
                messageTaskRunSnapshot.setErrorMsg(taskLogCollect.getErrorMsg());
                //当前任务执行时 线程池内的活跃线程数
                messageTaskRunSnapshot.setActiveCount(taskLogCollect.getActiveCount());
                //当前任务执行时 当前线程池内队列的大小
                messageTaskRunSnapshot.setQueueRemainingCapacity(taskLogCollect.getQueueRemainingCapacity());
                //当前任务执行时 当前线程池的任务堆积数量
                messageTaskRunSnapshot.setQueueSize(taskLogCollect.getQueueSize());
                return messageTaskRunSnapshot;
            }).collect(Collectors.toList());
            //保存数据
            messageTaskRunSnapshotService.batchSave(taskRunLogData);
        }

    }
}
