package com.dting.show.server.processing;

import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.show.datas.TaskInfoMessage;
import com.dting.show.server.buffers.TaskRunLogDataBufferReactor;
import com.dting.show.server.utils.DtingMessageSupplement;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author huangfu
 * @date shiji\
 */
public class TaskInfoServerBusinessProcessing extends DtingSimpleChannelInboundHandler<TaskInfoMessage> {

    private final TaskRunLogDataBufferReactor taskRunLogDataBufferReactor;

    public TaskInfoServerBusinessProcessing(TaskRunLogDataBufferReactor taskRunLogDataBufferReactor) {
        this.taskRunLogDataBufferReactor = taskRunLogDataBufferReactor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TaskInfoMessage taskInfoMessage) throws Exception {
        DtingMessageSupplement.supplement(channelHandlerContext, taskInfoMessage);
        taskRunLogDataBufferReactor.addMaterial(taskInfoMessage);
    }
}
