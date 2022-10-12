package com.dting.message.client.handler;

import com.dting.message.client.cache.ServerCommunicationConnectionPool;
import com.dting.message.common.agreement.packet.ConnectionMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 连接监控处理器
 *
 * @author huangfu
 * @date 2022年10月12日09:22:17
 */
public class ConnectionMonitoringHandler extends SimpleChannelInboundHandler<ConnectionMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(">>>>>>>>>>>连接服务端成功<<<<<<<<<<<<<");
        ServerCommunicationConnectionPool.addConnection(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(">>>>>>>>>>>服务连接关闭<<<<<<<<<<<<<");
        ServerCommunicationConnectionPool.removeConnection(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ConnectionMessage connectionMessage) throws Exception {

    }
}
