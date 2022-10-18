package com.dting.show.server.boot;

import com.dting.message.common.MessageCommunicationConfig;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.message.server.DtingMessageServerQuickStart;
import com.dting.message.server.config.MessageServerConfig;
import com.dting.show.server.config.DtingMessageConfig;
import com.dting.show.server.utils.SpringUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

/**
 * Dting消息服务启动引导
 *
 * @author huangfu
 * @date 2022年10月18日11:53:36
 */
public class DtingMessageServerBoot implements InitializingBean, DisposableBean {

    private final DtingMessageConfig properties;
    private final MessageCommunicationConfig messageCommunicationConfig;

    private DtingMessageServerQuickStart quickStart;
    public DtingMessageServerBoot(DtingMessageConfig properties, MessageCommunicationConfig messageCommunicationConfig) {
        this.properties = properties;
        this.messageCommunicationConfig = messageCommunicationConfig;
    }

    @Override
    public void destroy() throws Exception {
        //关闭服务
        quickStart.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageServerConfig config = new MessageServerConfig(messageCommunicationConfig, properties.getPort());
        Map<String, DtingSimpleChannelInboundHandler> dtingSimpleChannelInboundHandlerMap = SpringUtil.getBeansOfType(DtingSimpleChannelInboundHandler.class);
        dtingSimpleChannelInboundHandlerMap.forEach(config::addServerBusinessProcessing);
        quickStart = new DtingMessageServerQuickStart(config);
        //启动服务
        quickStart.start();
    }
}
