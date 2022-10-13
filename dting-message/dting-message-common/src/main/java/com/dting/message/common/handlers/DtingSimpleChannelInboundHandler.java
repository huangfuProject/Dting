package com.dting.message.common.handlers;

import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huangfu
 * @date
 */
@ChannelHandler.Sharable
public abstract class DtingSimpleChannelInboundHandler<I> extends SimpleChannelInboundHandler<I> {

}
