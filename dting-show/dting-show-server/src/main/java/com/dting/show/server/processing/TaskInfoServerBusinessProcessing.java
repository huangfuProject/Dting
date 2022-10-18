package com.dting.show.server.processing;

import com.alibaba.fastjson.JSON;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.show.datas.TaskInfoMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author huangfu
 * @date shiji\
 */
public class TaskInfoServerBusinessProcessing extends DtingSimpleChannelInboundHandler<TaskInfoMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TaskInfoMessage taskInfoMessage) throws Exception {
        System.out.println(JSON.toJSONString(taskInfoMessage));
    }
}
