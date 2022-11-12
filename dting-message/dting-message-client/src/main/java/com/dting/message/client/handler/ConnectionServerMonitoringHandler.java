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
public class ConnectionServerMonitoringHandler extends SimpleChannelInboundHandler<ConnectionMessage> {

    /**
     * 实例名称
     */
    private final String instanceKey;

    /**
     * 服务的环境
     */
    private final String serverEnv;

    /**
     * 服务的名称
     */
    private final String serverKey;

    public ConnectionServerMonitoringHandler(String instanceKey, String serverEnv, String serverKey) {
        this.instanceKey = instanceKey;
        this.serverEnv = serverEnv;
        this.serverKey = serverKey;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(">>>>>>>>>>>连接服务端成功<<<<<<<<<<<<<");
        ConnectionMessage connectionMessage = new ConnectionMessage();
        connectionMessage.setServerEnv(serverEnv);
        connectionMessage.setServerKey(serverKey);
        connectionMessage.setInstanceKey(instanceKey);
        ServerCommunicationConnectionPool.addConnection(ctx.channel(), instanceKey, serverEnv, serverKey);
        ctx.writeAndFlush(connectionMessage);
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
