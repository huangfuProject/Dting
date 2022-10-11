package com.dting.message.common.packet;

import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.serializes.ProtostuffSerialize;

/**
 * @author huangfu
 * @date
 */
public class DtingMessageTest extends DtingMessage {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        DtingMessageTest dtingMessageTest = new DtingMessageTest();
        dtingMessageTest.setName("huangfu");

        ProtostuffSerialize<DtingMessage> serialize = new ProtostuffSerialize<>();
        ProtostuffSerialize<DtingMessage> serialize1 = new ProtostuffSerialize<>();

        //将一个对象序列化
        byte[] bytes = serialize.serializeObject(dtingMessageTest);

        //将序列化好的对象执行第一次反序列化 获取父类
        DtingMessage dtingMessage = serialize1.objectDeserialize(bytes, DtingMessage.class);

        if(dtingMessage instanceof DtingMessageTest) {
            DtingMessageTest a = (DtingMessageTest)dtingMessage;
            System.out.println(a.getName());
        }

    }
}
