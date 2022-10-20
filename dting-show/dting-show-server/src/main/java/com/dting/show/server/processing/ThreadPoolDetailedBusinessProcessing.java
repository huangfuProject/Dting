package com.dting.show.server.processing;

import com.alibaba.fastjson.JSON;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.show.datas.ThreadPoolDetailedConfigMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * 线程池状态注册上报
 *
 * @author huangfu
 * @date 2022年10月19日09:01:46
 */
public class ThreadPoolDetailedBusinessProcessing extends DtingSimpleChannelInboundHandler<ThreadPoolDetailedConfigMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ThreadPoolDetailedConfigMessage threadPoolDetailedConfigMessage) throws Exception {
        System.out.println(JSON.toJSONString(threadPoolDetailedConfigMessage));
    }
}
