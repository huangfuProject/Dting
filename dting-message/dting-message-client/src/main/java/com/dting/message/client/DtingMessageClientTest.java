package com.dting.message.client;


import com.dting.message.client.cache.ServerCommunicationConnectionPool;
import com.dting.message.client.config.MessageClientConfig;
import com.dting.message.common.MessageTest;

import java.util.Scanner;

class DtingMessageClientTest {

    public static void main(String[] args) throws InterruptedException {
        MessageClientConfig config = new MessageClientConfig("127.0.0.1", 8888);
        DtingMessageClient dtingMessageClient = new DtingMessageClient(config);
        dtingMessageClient.connect();


        new Thread(() ->{
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                if("c".equals(line)) {
                    try {
                        dtingMessageClient.close();
                        break;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }

                ServerCommunicationConnectionPool.asyncAllSendMessage(new MessageTest("huangfikeixngashjhdsajksad"));
            }
        }).start();

        dtingMessageClient.await();
    }

}