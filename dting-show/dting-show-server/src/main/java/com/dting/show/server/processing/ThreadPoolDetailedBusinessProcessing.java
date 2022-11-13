package com.dting.show.server.processing;

import com.dting.message.common.Communication;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.show.datas.ThreadPoolDetailedConfigMessage;
import com.dting.show.server.buffers.ThreadPoolDetailedConfigReactor;
import com.dting.show.server.utils.DtingMessageSupplement;
import io.netty.channel.ChannelHandlerContext;

/**
 * 线程池状态注册上报
 *
 * @author huangfu
 * @date 2022年10月19日09:01:46
 */
public class ThreadPoolDetailedBusinessProcessing extends DtingSimpleChannelInboundHandler<ThreadPoolDetailedConfigMessage> {

    private final ThreadPoolDetailedConfigReactor threadPoolDetailedConfigReactor;

    public ThreadPoolDetailedBusinessProcessing(ThreadPoolDetailedConfigReactor threadPoolDetailedConfigReactor) {
        this.threadPoolDetailedConfigReactor = threadPoolDetailedConfigReactor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ThreadPoolDetailedConfigMessage threadPoolDetailedConfigMessage) throws Exception {
        DtingMessageSupplement.supplement(channelHandlerContext, threadPoolDetailedConfigMessage);
        //包装为一个通讯设施
        Communication communication = new Communication(channelHandlerContext.channel(), threadPoolDetailedConfigMessage.getInstanceKey(), threadPoolDetailedConfigMessage.getServerEnv(), threadPoolDetailedConfigMessage.getServerKey(), threadPoolDetailedConfigMessage.getTimeout());
        //提交到处理的线程池中
        threadPoolDetailedConfigReactor.addMaterial(new ThreadPoolDetailedConfigReactor.ReactionMaterial(communication, threadPoolDetailedConfigMessage));
    }
}
