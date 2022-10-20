package com.dting.show.datas;

import com.dting.common.datas.TaskLogCollect;
import com.dting.message.common.agreement.packet.DtingMessage;

import java.io.Serializable;

/**
 * 任务信息消息对象
 *
 * @author huangfu
 * @date 2022年10月17日14:05:22
 */
public class TaskInfoMessage extends DtingMessage implements Serializable {

    private static final long serialVersionUID = 4429958958843848431L;

    private final TaskLogCollect taskLogCollect;

    public TaskInfoMessage(TaskLogCollect taskLogCollect) {
        this.taskLogCollect = taskLogCollect;
    }

    public TaskLogCollect getTaskInfo() {
        return taskLogCollect;
    }
}
