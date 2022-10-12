package com.dting.message.server.handler;

import com.dting.message.common.cache.CommunicationConnectionPool;
import com.dting.message.server.packet.ConnectionMessage;
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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ConnectionMessage connectionMessage) throws Exception {
        //获取连接消息的状态
        ConnectionMessage.ConnectionStatus connectionStatus = connectionMessage.getConnectionStatus();

        if (ConnectionMessage.ConnectionStatus.OPEN.equals(connectionStatus)) {
            CommunicationConnectionPool.addConnection(channelHandlerContext.channel());
        } else {
            CommunicationConnectionPool.removeConnection(channelHandlerContext.channel());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        CommunicationConnectionPool.removeConnection(ctx.channel());
    }
}
