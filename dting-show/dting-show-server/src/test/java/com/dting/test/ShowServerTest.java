package com.dting.test;

import com.alibaba.fastjson.JSON;
import com.dting.message.common.MessageCommunicationConfig;
import com.dting.message.common.MessageTest;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.message.server.DtingMessageServerQuickStart;
import com.dting.message.server.config.MessageServerConfig;
import com.dting.show.datas.SystemInfoMessage;
import com.dting.show.datas.TaskInfoMessage;
import com.dting.show.server.processing.SystemInfoBusinessProcessing;
import com.dting.show.server.processing.TaskInfoServerBusinessProcessing;
import io.netty.channel.ChannelHandlerContext;

import java.util.Scanner;

/**
 * 测试类
 *
 * @author huangfu
 * @date 2022年10月17日15:06:41
 */
public class ShowServerTest {
    public static void main(String[] args) throws InterruptedException {
        MessageServerConfig config = new MessageServerConfig(new MessageCommunicationConfig(), 8888);
        DtingMessageServerQuickStart quickStart = new DtingMessageServerQuickStart(config);

        config.addServerBusinessProcessing("test", new TaskInfoServerBusinessProcessing());
        config.addServerBusinessProcessing("test1", new SystemInfoBusinessProcessing());
        Thread thread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();

            if (line.equals("c")) {
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
