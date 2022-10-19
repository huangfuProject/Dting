package com.dting.show.server.utils;

import cn.hutool.core.util.StrUtil;
import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.utils.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * Dting消息补充
 *
 * @author huangfu
 * @date 2022年10月19日17:26:54
 */
public class DtingMessageSupplement {

    /**
     * 消息的完整性补充
     *
     * @param channelHandlerContext 通道上下文
     * @param dtingMessage          消息对象
     */
    public static void supplement(ChannelHandlerContext channelHandlerContext, DtingMessage dtingMessage) {
        String messageSourceIp = dtingMessage.getMessageSourceAddress();
        if (StrUtil.isBlank(messageSourceIp)) {
            String messageSourceAddress = ChannelUtil.getChannelRemoteAddress(channelHandlerContext.channel());
            dtingMessage.setMessageSourceAddress(messageSourceAddress);
        }
    }
}
