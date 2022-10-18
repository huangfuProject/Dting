package com.dting.message.common;

import com.dting.message.common.agreement.AgreementChoreography;
import com.dting.message.common.agreement.DefaultAgreementChoreography;
import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.serializes.DtingSerialize;
import com.dting.message.common.serializes.ProtostuffSerialize;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * *************************************************<br/>
 * 消息处理过程中出现的处理器等操作的暂存区<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/9 9:39
 */
public class DefaultMessageCommunicationConfig implements MessageCommunicationConfig {
    /**
     * 网络数据包的编解码处理程序
     */
    private AgreementChoreography agreementChoreography = new DefaultAgreementChoreography();

    /**
     * 对象序列化和反序列化的工具包
     */
    private DtingSerialize<DtingMessage> dtingSerialize = new ProtostuffSerialize<>();

    @Override
    public AgreementChoreography getAgreementChoreography() {
        return agreementChoreography;
    }

    @Override
    public void setAgreementChoreography(AgreementChoreography agreementChoreography) {
        this.agreementChoreography = agreementChoreography;
    }

    @Override
    public DtingSerialize<DtingMessage> getDtingSerialize() {
        return dtingSerialize;
    }

    @Override
    public void setDtingSerialize(DtingSerialize<DtingMessage> dtingSerialize) {
        this.dtingSerialize = dtingSerialize;
    }
}
