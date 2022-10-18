package com.dting.sdk.collects;

import com.dting.common.datas.NetInfo;
import com.dting.common.datas.SystemCpuGroup;
import com.dting.common.datas.SystemMemory;
import com.dting.common.datas.SystemPropertiesAbstract;
import com.dting.sdk.reactor.MessageBuffer;
import com.dting.sdk.reactor.MessageReactor;
import com.dting.show.datas.SystemInfoMessage;
import com.dting.utils.SystemPropertiesUtil;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 指标采集器
 *
 * @author huangfu
 * @date 2022年10月18日09:26:56
 */
public class MetricsCollectedHandler {

    private final static ScheduledThreadPoolExecutor COLLECTED = new ScheduledThreadPoolExecutor(1,r -> {
        Thread thread = new Thread(r);
        thread.setName("MetricsCollected");
        return thread;
    });

    public static void run(){
        COLLECTED.scheduleWithFixedDelay(() -> {
            try {
                SystemPropertiesAbstract envAbstract = SystemPropertiesUtil.getEnvAbstract();
                SystemCpuGroup systemCpuGroup = SystemPropertiesUtil.systemCpu();
                SystemMemory systemMemory = SystemPropertiesUtil.getSystemMemory();
                List<NetInfo> netInfos = SystemPropertiesUtil.systemNetwork();
                SystemInfoMessage systemInfoMessage = new SystemInfoMessage();
                systemInfoMessage.setSystemMemory(systemMemory);
                systemInfoMessage.setNetInfos(netInfos);
                systemInfoMessage.setSystemCpuGroup(systemCpuGroup);
                systemInfoMessage.setSystemPropertiesAbstract(envAbstract);

                MessageBuffer messageBuffer = new MessageBuffer();
                messageBuffer.setDtingMessage(systemInfoMessage);
                MessageReactor.sendMessage(messageBuffer);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }, 10L, 30, TimeUnit.SECONDS);
    }

    public static void stop(){
        COLLECTED.shutdown();
    }

}
