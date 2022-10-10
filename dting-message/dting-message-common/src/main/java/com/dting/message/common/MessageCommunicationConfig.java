package com.dting.message.common;

import com.dting.message.common.agreement.DefaultMessageAgreementLayout;
import com.dting.message.common.agreement.MessageAgreementLayout;
import com.dting.message.common.serializes.DtingSerialize;
import com.dting.message.common.serializes.ProtostuffSerialize;

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
     * 消息协议体编排处理器
     */
    private MessageAgreementLayout agreementLayout = new DefaultMessageAgreementLayout();

    /**
     * 默认的对象序列化协议
     */
    private DtingSerialize dtingSerialize = new ProtostuffSerialize();

    public MessageAgreementLayout getAgreementLayout() {
        return agreementLayout;
    }

    public void setAgreementLayout(MessageAgreementLayout agreementLayout) {
        this.agreementLayout = agreementLayout;
    }

    public DtingSerialize getDtingSerialize() {
        return dtingSerialize;
    }

    public void setDtingSerialize(DtingSerialize dtingSerialize) {
        this.dtingSerialize = dtingSerialize;
    }
}
