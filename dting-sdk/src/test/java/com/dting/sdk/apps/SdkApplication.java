package com.dting.sdk.apps;

import com.dting.message.client.config.MessageClientConfig;
import com.dting.sdk.apps.services.ApplicationService;
import com.dting.sdk.processing.ThreadConfigUpdateProcessing;
import com.dting.sdk.reactor.MessageReactor;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * *************************************************<br/>
 * 应用<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/7 15:35
 */
public class SdkApplication {

    public static void main(String[] args) throws InterruptedException {
        ApplicationService applicationService = new ApplicationService();
        MessageClientConfig config = new MessageClientConfig("127.0.0.1", 8888);
        config.setServerEnv("dev");
        config.setServerKey("server-dev");
        config.setInstanceKey("instance01");
        config.setTimeout(TimeUnit.HOURS.toMillis(5));
        config.addClientBusinessProcessing(new ThreadConfigUpdateProcessing());
        MessageReactor.start(Collections.singletonList(config));
        Random random = new Random();
        while (true) {
            int i1 = random.nextInt(700);
            for (int i = 0; i < i1; i++) {
                applicationService.app();
            }

            Thread.sleep(i1 * 100);
        }
    }
}
