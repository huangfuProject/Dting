package com.dting.show.server.buffers;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.common.datas.TaskInfo;
import com.dting.show.datas.TaskInfoMessage;
import com.dting.show.server.entity.MessageTaskRunLogData;
import com.dting.show.server.service.MessageTaskRunLogDataService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务执行日志数据反应堆
 *
 * @author huangfu
 * @date 2022年10月20日11:21:38
 */
public class TaskRunLogDataBufferReactor extends MessageBufferReactor<TaskInfoMessage> {

    private final MessageTaskRunLogDataService messageTaskRunLogDataService;

    public TaskRunLogDataBufferReactor(MessageTaskRunLogDataService messageTaskRunLogDataService) {
        this.messageTaskRunLogDataService = messageTaskRunLogDataService;
    }

    @Override
    public void start0(List<TaskInfoMessage> sources) {
        if (CollectionUtil.isNotEmpty(sources)) {
            List<MessageTaskRunLogData> taskRunLogData = sources.stream().map(source -> {
                MessageTaskRunLogData messageTaskRunLogData = new MessageTaskRunLogData();
                messageTaskRunLogData.commonDataSetting(source);
                TaskInfo taskInfo = source.getTaskInfo();
                //人物名
                messageTaskRunLogData.setThreadPoolName(taskInfo.getThreadPoolName());
                // 执行组
                messageTaskRunLogData.setThreadPoolGroupName(String.format("%s:%s", source.getMessageSourceAddress(), taskInfo.getThreadPoolName()));
                //任务数量
                messageTaskRunLogData.setTaskName(taskInfo.getTaskName());
                //开始时间
                messageTaskRunLogData.setStartTime(taskInfo.getStartTime());
                //结束数量
                messageTaskRunLogData.setEndTime(taskInfo.getEndTime());
                //是否成功
                messageTaskRunLogData.setSuccess(taskInfo.getSuccess() ? "0" : "1");
                //是否被拒绝
                messageTaskRunLogData.setRejected(taskInfo.getRejected() ? "1" : "0");
                //失败信息
                messageTaskRunLogData.setErrorMsg(taskInfo.getErrorMsg());
                //当前任务执行时 线程池内的活跃线程数
                messageTaskRunLogData.setActiveCount(taskInfo.getActiveCount());
                //当前任务执行时 当前线程池内队列的大小
                messageTaskRunLogData.setQueueRemainingCapacity(taskInfo.getQueueRemainingCapacity());
                //当前任务执行时 当前线程池的任务堆积数量
                messageTaskRunLogData.setQueueSize(taskInfo.getQueueSize());
                return messageTaskRunLogData;
            }).collect(Collectors.toList());
            //保存数据
            messageTaskRunLogDataService.ignoreOnlyBatchSave(taskRunLogData);
        }

    }
}
