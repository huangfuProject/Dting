package com.dting.message.server;


import com.dting.message.common.MessageCommunicationConfig;
import com.dting.message.server.config.MessageServerConfig;

import java.util.Scanner;

public class DtingMessageServerQuickStartTest {

    public static void main(String[] args) throws InterruptedException {
        DtingMessageServerQuickStart quickStart = new DtingMessageServerQuickStart(new MessageServerConfig(new MessageCommunicationConfig(), 8888));

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