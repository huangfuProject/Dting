package com.dting.message.common;

import com.dting.message.common.agreement.DefaultAgreementChoreography;
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
     * 网络数据包的编解码处理程序
     */
    private DefaultAgreementChoreography agreementChoreography = new DefaultAgreementChoreography();

    /**
     * 对象序列化和反序列化的工具包
     */
    private DtingSerialize dtingSerialize = new ProtostuffSerialize();

    public DefaultAgreementChoreography getAgreementChoreography() {
        return agreementChoreography;
    }

    public void setAgreementChoreography(DefaultAgreementChoreography agreementChoreography) {
        this.agreementChoreography = agreementChoreography;
    }

    public DtingSerialize getDtingSerialize() {
        return dtingSerialize;
    }

    public void setDtingSerialize(DtingSerialize dtingSerialize) {
        this.dtingSerialize = dtingSerialize;
    }
}
