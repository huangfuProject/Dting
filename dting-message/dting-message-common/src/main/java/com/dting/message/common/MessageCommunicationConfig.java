package com.dting.message.common;

import com.dting.message.common.agreement.AgreementChoreography;
import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.serializes.DtingSerialize;

/**
 * 消息的编解码处理器
 *
 * @author huangfu
 * @date 2022年10月18日12:02:40
 */
public interface MessageCommunicationConfig {

    /**
     * 返回 网络数据包的编解码处理程序
     *
     * @return 网络数据包的编解码处理程序
     */
    AgreementChoreography getAgreementChoreography();

    /**
     * 设置 网络数据包的编解码处理程序
     *
     * @param agreementChoreography 网络数据包的编解码处理程序
     */
    void setAgreementChoreography(AgreementChoreography agreementChoreography);

    /**
     * 返回 对象序列化和反序列化的工具包
     *
     * @return 对象序列化和反序列化的工具包
     */
    DtingSerialize<DtingMessage> getDtingSerialize();

    /**
     * 设置 对象序列化和反序列化的工具包
     *
     * @param dtingSerialize 对象序列化和反序列化的工具包
     */
    void setDtingSerialize(DtingSerialize<DtingMessage> dtingSerialize);
}
