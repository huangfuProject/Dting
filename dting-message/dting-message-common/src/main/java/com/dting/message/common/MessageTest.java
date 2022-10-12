package com.dting.message.common;

import com.dting.message.common.agreement.packet.DtingMessage;

/**
 * @author huangfu
 * @date
 */
public class MessageTest extends DtingMessage {

    private final String name;

    public MessageTest(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
