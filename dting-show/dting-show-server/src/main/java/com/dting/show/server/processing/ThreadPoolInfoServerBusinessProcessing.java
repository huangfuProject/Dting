package com.dting.show.server.processing;

import com.alibaba.fastjson.JSON;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.show.datas.ThreadPoolInfoMessage;
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
public class ThreadPoolInfoServerBusinessProcessing extends DtingSimpleChannelInboundHandler<ThreadPoolInfoMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ThreadPoolInfoMessage threadPoolInfoMessage) throws Exception {
        System.out.println(JSON.toJSONString(threadPoolInfoMessage));
    }
}
