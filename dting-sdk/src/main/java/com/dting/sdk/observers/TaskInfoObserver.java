package com.dting.sdk.observers;

import cn.hutool.core.bean.BeanUtil;
import com.dting.DtingObserver;
import com.dting.model.TaskInfoSubject;
import com.dting.sdk.reactor.MessageBuffer;
import com.dting.sdk.reactor.MessageReactor;
import com.dting.show.datas.TaskInfoMessage;

/**
 * 任务配置详情的监听者
 *
 * @author huangfu
 * @date 2022年10月17日13:38:31
 */
public class TaskInfoObserver extends DtingObserver<TaskInfoSubject> {
    @Override
    public void doUpdate(TaskInfoSubject data) {
        //包装为网络数据包结构
        TaskInfoMessage taskInfoMessage = new TaskInfoMessage(data.getTaskInfo());
        MessageBuffer messageBuffer = new MessageBuffer();
        messageBuffer.setDtingMessage(taskInfoMessage);
        MessageReactor.sendMessage(messageBuffer);
    }
}
