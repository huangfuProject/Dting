package com.dting.message.common.agreement;

import com.dting.message.common.agreement.implementation.MessageAgreementLayout;
import com.dting.message.common.agreement.implementation.PacketSegmentationHandler;

/**
 * 协编解码器的编排<br/>
 * <p>
 * 返回一个 网路传输数据包的拆包处理程序<br/>
 * 返回一个对于一个完整数据包的编解码动作处理器<br/>
 *
 * @author huangfu
 * @date 2022年10月10日15:23:41
 */
public interface AgreementChoreography {

    /**
     * 返回一个 网路传输数据包的拆包处理程序
     *
     * @return 网路传输数据包的拆包处理程序
     */
    PacketSegmentationHandler segmentationHandler();

    /**
     * 返回一个对于一个完整数据包的编解码动作处理器
     *
     * @return 一个完整数据包的编解码动作处理器
     */
    MessageAgreementLayout messageAgreementLayout();
}
