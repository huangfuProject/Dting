package com.dting.message.common;

import com.dting.message.common.agreement.DefaultMessageAgreementLayout;
import com.dting.message.common.agreement.MessageAgreementLayout;

/**
 * *************************************************<br/>
 * 消息处理过程中出现的处理器等操作的暂存区<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/9 9:39
 */
public class MessageContext {
    /**
     * 消息协议体编排处理器
     */
    private MessageAgreementLayout agreementLayout = new DefaultMessageAgreementLayout();

    public MessageAgreementLayout getAgreementLayout() {
        return agreementLayout;
    }

    public void setAgreementLayout(MessageAgreementLayout agreementLayout) {
        this.agreementLayout = agreementLayout;
    }
}
