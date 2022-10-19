package com.dting.show.server.processing;

import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.show.datas.SystemInfoMessage;
import com.dting.show.server.buffers.MessageCpuDataBufferReactor;
import com.dting.show.server.utils.DtingMessageSupplement;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author huangfu
 * @date
 */
public class SystemInfoBusinessProcessing extends DtingSimpleChannelInboundHandler<SystemInfoMessage> {

    private final MessageCpuDataBufferReactor messageCpuDataBufferReactor;

    public SystemInfoBusinessProcessing(MessageCpuDataBufferReactor messageCpuDataBufferReactor) {
        this.messageCpuDataBufferReactor = messageCpuDataBufferReactor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SystemInfoMessage systemInfoMessage) throws Exception {
        DtingMessageSupplement.supplement(channelHandlerContext, systemInfoMessage);
        messageCpuDataBufferReactor.addMaterial(systemInfoMessage);
    }

}
