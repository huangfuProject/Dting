package com.dting.message.common.agreement;

import com.dting.message.common.agreement.implementation.DefaultMessageAgreementLayout;
import com.dting.message.common.agreement.implementation.DefaultPacketSegmentationHandler;
import com.dting.message.common.agreement.implementation.MessageAgreementLayout;
import com.dting.message.common.agreement.implementation.PacketSegmentationHandler;

/**
 * 默认的协议编排处理器
 *
 * @author huangfu
 * @date 2022年10月10日15:28:17
 */
public class DefaultAgreementChoreography implements AgreementChoreography {
    @Override
    public PacketSegmentationHandler segmentationHandler() {
        return new DefaultPacketSegmentationHandler();
    }

    @Override
    public MessageAgreementLayout messageAgreementLayout() {
        return new DefaultMessageAgreementLayout();
    }
}
