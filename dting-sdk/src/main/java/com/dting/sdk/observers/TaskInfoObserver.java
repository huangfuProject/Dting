package com.dting.sdk.observers;

import cn.hutool.core.bean.BeanUtil;
import com.dting.DtingObserver;
import com.dting.model.TaskInfo;
import com.dting.sdk.reactor.MessageBuffer;
import com.dting.sdk.reactor.MessageReactor;
import com.dting.show.datas.TaskInfoMessage;

/**
 * 任务配置详情的监听者
 *
 * @author huangfu
 * @date 2022年10月17日13:38:31
 */
public class TaskInfoObserver extends DtingObserver<TaskInfo> {
    @Override
    public void doUpdate(TaskInfo data) {
        TaskInfoMessage taskInfoMessage = new TaskInfoMessage();
        BeanUtil.copyProperties(data, taskInfoMessage);
        MessageBuffer messageBuffer = new MessageBuffer();
        messageBuffer.setDtingMessage(taskInfoMessage);
        MessageReactor.sendMessage(messageBuffer);
    }
}
