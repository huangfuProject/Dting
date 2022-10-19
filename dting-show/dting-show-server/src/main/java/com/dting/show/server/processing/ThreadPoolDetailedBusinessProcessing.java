package com.dting.show.server.processing;

import com.alibaba.fastjson.JSON;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.show.datas.ThreadPoolDetailedMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * 线程池状态注册上报
 *
 * @author huangfu
 * @date 2022年10月19日09:01:46
 */
public class ThreadPoolDetailedBusinessProcessing extends DtingSimpleChannelInboundHandler<ThreadPoolDetailedMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ThreadPoolDetailedMessage threadPoolDetailedMessage) throws Exception {
        System.out.println(JSON.toJSONString(threadPoolDetailedMessage));
    }
}
