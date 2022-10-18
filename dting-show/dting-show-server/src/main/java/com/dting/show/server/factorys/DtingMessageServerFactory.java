package com.dting.show.server.factorys;

import com.dting.message.common.DefaultMessageCommunicationConfig;
import com.dting.message.common.MessageCommunicationConfig;
import com.dting.show.server.boot.DtingMessageServerBoot;
import com.dting.show.server.config.DtingMessageConfig;
import com.dting.show.server.utils.SpringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 消息服务启动器
 *
 * @author huangfu
 * @date 2022年10月18日11:54:14
 */
@Configuration
@EnableConfigurationProperties(DtingMessageConfig.class)
public class DtingMessageServerFactory {

    private final DtingMessageConfig properties;

    public DtingMessageServerFactory(DtingMessageConfig properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageCommunicationConfig defaultMessageCommunicationConfig(){
        return new DefaultMessageCommunicationConfig();
    }

    @Bean
    @DependsOn("springUtil")
    public DtingMessageServerBoot dtingMessageServerBoot(MessageCommunicationConfig messageCommunicationConfig){
        return new DtingMessageServerBoot(properties, messageCommunicationConfig);
    }

    @Bean(name = "springUtil")
    public SpringUtil springUtil(){
        return new SpringUtil();
    }
}
