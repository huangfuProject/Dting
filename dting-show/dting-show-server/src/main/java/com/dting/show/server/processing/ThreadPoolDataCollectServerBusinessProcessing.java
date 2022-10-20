package com.dting.show.server.processing;

import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.show.datas.ThreadPoolDataCollectMessage;
import com.dting.show.server.buffers.ThreadPoolDataBufferReactor;
import com.dting.show.server.utils.DtingMessageSupplement;
import io.netty.channel.ChannelHandlerContext;

/**
 * *************************************************<br/>
 * <p>
 * 线程池执行状态的采集数据处理器<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/18 21:16
 */
public class ThreadPoolDataCollectServerBusinessProcessing extends DtingSimpleChannelInboundHandler<ThreadPoolDataCollectMessage> {

    private final ThreadPoolDataBufferReactor threadPoolDataBufferReactor;

    public ThreadPoolDataCollectServerBusinessProcessing(ThreadPoolDataBufferReactor threadPoolDataBufferReactor) {
        this.threadPoolDataBufferReactor = threadPoolDataBufferReactor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ThreadPoolDataCollectMessage threadPoolDataCollectMessage) throws Exception {
        DtingMessageSupplement.supplement(channelHandlerContext, threadPoolDataCollectMessage);
        threadPoolDataBufferReactor.addMaterial(threadPoolDataCollectMessage);
    }
}
