package com.dting.message.client;

import com.dting.message.client.cache.ServerCommunicationConnectionPool;
import com.dting.message.client.config.MessageClientConfig;
import com.dting.message.client.handler.ConnectionMonitoringHandler;
import com.dting.message.common.MessageCommunicationConfig;
import com.dting.message.common.agreement.AgreementChoreography;
import com.dting.message.common.agreement.implementation.PacketSegmentationHandler;
import com.dting.message.common.agreement.packet.ConnectionMessage;
import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.message.common.handlers.PacketCodecHandler;
import com.dting.message.common.utils.NettyEventLoopUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;
import java.util.Map;


/**
 * 服务客户端
 *
 * @author huangfu
 * @date 2022年10月12日18:25:07
 */
public class DtingMessageClient {

    /**
     * 客户端连接配置项
     */
    private final MessageClientConfig config;

    /**
     * 与服务端的通讯管道
     */
    private Channel channel;

    /**
     * 启动器
     */
    private Bootstrap bootstrap;

    public DtingMessageClient(MessageClientConfig config) {
        this.config = config;
    }

    private static final int DEFAULT_CONNECT_TIMEOUT = 3000;

    private final static int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);

    private static final EventLoopGroup EVENT_LOOP_GROUP = NettyEventLoopUtils.eventLoopGroup(DEFAULT_IO_THREADS, "NettyClientWorker");

    /**
     * 开始连接服务端
     */
    public void connect() {
        bootstrap = new Bootstrap();
        bootstrap.group(EVENT_LOOP_GROUP)
                .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NettyEventLoopUtils.socketChannelClass());
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DEFAULT_CONNECT_TIMEOUT);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                //获取消息协议上下文
                MessageCommunicationConfig communicationConfig = config.getCommunicationConfig();

                //获取网络数据包分割器
                AgreementChoreography agreementChoreography = communicationConfig.getAgreementChoreography();
                PacketSegmentationHandler packetSegmentationHandler = agreementChoreography.segmentationHandler();

                //获取业务模型处理器
                Map<String, DtingSimpleChannelInboundHandler<? extends DtingMessage>> businessProcessingUnit = config.getClientBusinessProcessingUnit();
                //数据通讯管道编排
                //写入数据包分割器
                socketChannel.pipeline().addLast("PacketSegmentationHandler", packetSegmentationHandler);
                //写入数据编解码器
                socketChannel.pipeline().addLast("PacketCodecHandler", new PacketCodecHandler(communicationConfig));
                //连接监控
                socketChannel.pipeline().addLast("ConnectionMonitoringHandler", new ConnectionMonitoringHandler());
                //开始写入业务处理器
                businessProcessingUnit.forEach((handlerName, handler) -> socketChannel.pipeline().addLast(handlerName, handler));
            }
        });

        //开始连接服务器
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(config.getHost(), config.getPort())).syncUninterruptibly();
        channel = channelFuture.channel();
        //发送一个开启事件
        channel.writeAndFlush(new ConnectionMessage());
    }

    /**
     * 等到服务端关闭
     *
     * @throws InterruptedException 中断异常
     */
    public void await() throws InterruptedException {
        channel.closeFuture().sync();
    }

    /**
     * 关闭服务端
     *
     * @throws Throwable 异常信息
     */
    public void close() throws Throwable {
        if (bootstrap != null) {
            EVENT_LOOP_GROUP.shutdownGracefully().syncUninterruptibly();
        }
    }
}
