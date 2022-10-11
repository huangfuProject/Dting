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
public class MessageCommunicationConfig {
    /**
     * 网络数据包的编解码处理程序
     */
    private AgreementChoreography agreementChoreography = new DefaultAgreementChoreography();

    /**
     * 对象序列化和反序列化的工具包
     */
    private DtingSerialize<DtingMessage> dtingSerialize = new ProtostuffSerialize<>();

    /**
     * 构建一个业务处理器
     */
    private final Map<String, SimpleChannelInboundHandler<? extends DtingMessage>> businessProcessingUnit = new ConcurrentHashMap<>(8);

    public AgreementChoreography getAgreementChoreography() {
        return agreementChoreography;
    }

    public void setAgreementChoreography(AgreementChoreography agreementChoreography) {
        this.agreementChoreography = agreementChoreography;
    }

    public DtingSerialize<DtingMessage> getDtingSerialize() {
        return dtingSerialize;
    }

    public void setDtingSerialize(DtingSerialize<DtingMessage> dtingSerialize) {
        this.dtingSerialize = dtingSerialize;
    }

    /**
     * 追加一个业务处理器
     *
     * @param businessProcessingName 业务处理器的名称
     * @param handler                处理器
     */
    public void addBusinessProcessing(String businessProcessingName, SimpleChannelInboundHandler<? extends DtingMessage> handler) {
        businessProcessingUnit.put(businessProcessingName, handler);
    }

    /**
     * 追加一个业务处理器  默认的名称是类名
     *
     * @param handler 处理器
     */
    public void addBusinessProcessing(SimpleChannelInboundHandler<? extends DtingMessage> handler) {
        this.addBusinessProcessing(handler.getClass().getSimpleName(), handler);
    }

    /**
     * 返回构建的所有的业务处理器
     *
     * @return 业务处理器的拷贝对象
     */
    public Map<String, SimpleChannelInboundHandler<? extends DtingMessage>> getBusinessProcessingUnit() {
        Map<String, SimpleChannelInboundHandler<? extends DtingMessage>> businessProcessingUnitCopy = new ConcurrentHashMap<>(8);
        businessProcessingUnitCopy.putAll(businessProcessingUnit);
        return businessProcessingUnitCopy;
    }
}
