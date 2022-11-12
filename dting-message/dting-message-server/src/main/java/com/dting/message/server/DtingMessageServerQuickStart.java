package com.dting.message.server;

import cn.hutool.core.util.StrUtil;
import com.dting.message.common.MessageCommunicationConfig;
import com.dting.message.common.agreement.AgreementChoreography;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.message.common.handlers.PacketCodecHandler;
import com.dting.message.common.agreement.implementation.PacketSegmentationHandler;
import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.utils.NettyEventLoopUtils;
import com.dting.message.server.config.MessageServerConfig;
import com.dting.message.server.handler.ConnectionClientMonitoringHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * 服务端快速启动器
 *
 * @author huangfu
 * @date 2022年10月11日09:33:37
 */
public class DtingMessageServerQuickStart {

    /**
     * 服务端的默认工作线程池
     */
    private final static int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);

    /**
     * 分发管理器
     */
    private EventLoopGroup bossGroup;
    /**
     * 工作管理器
     */
    private EventLoopGroup workerGroup;

    /**
     * 启动引导器
     */
    private ServerBootstrap bootstrap;
    /**
     * 服务管道
     */
    private Channel channel;

    private final MessageServerConfig config;

    public DtingMessageServerQuickStart(MessageServerConfig config) {
        this.config = config;
    }

    /**
     * 启动一个服务端
     */
    public void start() {
        System.out.println(">>>>>>>>>>>>服务端启动准备<<<<<<<<<<<<<<<<");
        bossGroup = NettyEventLoopUtils.eventLoopGroup(1, "boss");
        workerGroup = NettyEventLoopUtils.eventLoopGroup(DEFAULT_IO_THREADS, "worker");

        bootstrap = new ServerBootstrap();
        //初始化netty解码器
        bootstrap.group(bossGroup, workerGroup)
                .channel(NettyEventLoopUtils.serverSocketChannelClass())
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.FALSE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //获取消息协议上下文
                        MessageCommunicationConfig communicationConfig = config.getCommunicationConfig();

                        //获取网络数据包分割器
                        AgreementChoreography agreementChoreography = communicationConfig.getAgreementChoreography();
                        PacketSegmentationHandler packetSegmentationHandler = agreementChoreography.segmentationHandler();

                        //获取业务模型处理器
                        Map<String, DtingSimpleChannelInboundHandler<? extends DtingMessage>> businessProcessingUnit = config.getServerBusinessProcessingUnit();
                        //数据通讯管道编排
                        //写入数据包分割器
                        socketChannel.pipeline().addLast("PacketSegmentationHandler", packetSegmentationHandler);
                        //写入数据编解码器
                        socketChannel.pipeline().addLast("PacketCodecHandler", new PacketCodecHandler(communicationConfig));
                        //写入连接监控
                        socketChannel.pipeline().addLast("ConnectionMonitoringHandler", new ConnectionClientMonitoringHandler());
                        //开始写入业务处理器
                        businessProcessingUnit.forEach((handlerName, handler) -> socketChannel.pipeline().addLast(handlerName, handler));
                    }
                });

        String host = config.getHost();
        InetSocketAddress bindAddress;
        if (StrUtil.isNotBlank(host)) {
            bindAddress = new InetSocketAddress(host, config.getPort());
        } else {
            //绑定一个本机对象  这里需要绑定一个地址
            bindAddress = new InetSocketAddress(config.getPort());
        }
        ChannelFuture channelFuture = bootstrap.bind(bindAddress);
        //等待绑定完成
        channelFuture.syncUninterruptibly();
        channel = channelFuture.channel();
        System.out.println(">>>>>>>>>>>>服务端启动完成<<<<<<<<<<<<<<<<");
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
     * @throws Exception 异常信息
     */
    public void close() throws Exception {
        if (bootstrap != null) {
            bossGroup.shutdownGracefully().syncUninterruptibly();
            workerGroup.shutdownGracefully().syncUninterruptibly();
        }
    }
}
