package com.dting.model;

import com.dting.Subject;
import com.dting.common.datas.TaskInfo;

import java.io.Serializable;

/**
 * 任务
 *
 * @author huangfu
 * @date 2022年10月18日08:54:14
 */
public class TaskInfoSubject extends Subject implements Serializable {
    private static final long serialVersionUID = 629962118724876789L;

    private final TaskInfo taskInfo;

    public TaskInfoSubject(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    @Override
    public String toString() {
        return "TaskInfoSubject{" + "taskInfo=" + taskInfo + '}';
    }
}
