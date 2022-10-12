package com.dting.message.server;


import com.dting.message.common.MessageCommunicationConfig;
import com.dting.message.common.MessageTest;
import com.dting.message.common.agreement.packet.DtingMessage;
import com.dting.message.common.packet.DtingMessageTest;
import com.dting.message.server.config.MessageServerConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;

public class DtingMessageServerQuickStartTest {

    public static void main(String[] args) throws InterruptedException {
        MessageServerConfig config = new MessageServerConfig(new MessageCommunicationConfig(), 8888);
        DtingMessageServerQuickStart quickStart = new DtingMessageServerQuickStart(config);

        config.addServerBusinessProcessing("test", new TestHandler());
        Thread thread = new Thread(() ->{
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();

            if(line.equals("c")) {
                try {
                    quickStart.close();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        quickStart.start();
        quickStart.await();
    }
}
@ChannelHandler.Sharable
class TestHandler extends SimpleChannelInboundHandler<MessageTest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageTest dtingMessageTest) throws Exception {
        System.out.println(dtingMessageTest);
    }
}