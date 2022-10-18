package com.dting.show.server.processing;

import com.alibaba.fastjson.JSON;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.show.datas.SystemInfoMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author huangfu
 * @date
 */
public class SystemInfoBusinessProcessing extends DtingSimpleChannelInboundHandler<SystemInfoMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SystemInfoMessage systemInfoMessage) throws Exception {
        System.out.println(JSON.toJSONString(systemInfoMessage));
    }
}
