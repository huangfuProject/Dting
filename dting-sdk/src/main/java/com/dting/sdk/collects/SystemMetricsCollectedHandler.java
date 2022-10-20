package com.dting.sdk.collects;

import com.dting.common.datas.NetDataCollect;
import com.dting.common.datas.SystemCpuGroupCollectCollect;
import com.dting.common.datas.SystemMemoryCollect;
import com.dting.common.datas.SystemPropertiesAbstractCollect;
import com.dting.sdk.reactor.MessageBuffer;
import com.dting.sdk.reactor.MessageReactor;
import com.dting.show.datas.SystemInfoMessage;
import com.dting.utils.SystemPropertiesUtil;

import java.util.List;
import java.util.concurrent.*;

/**
 * 操作系统指标采集器
 *
 * @author huangfu
 * @date 2022年10月18日09:26:56
 */
public class SystemMetricsCollectedHandler {

    private final static ScheduledThreadPoolExecutor COLLECTED = new ScheduledThreadPoolExecutor(1, r -> {
        Thread thread = new Thread(r);
        thread.setName("SystemMetricsCollected");
        return thread;
    });

    /**
     * 开始运行
     */
    public static void run() {
        COLLECTED.scheduleWithFixedDelay(() -> {
            try {
                //获取操作系统摘要
                SystemPropertiesAbstractCollect envAbstract = SystemPropertiesUtil.getEnvAbstract();
                //操作系统CPU指标
                SystemCpuGroupCollectCollect systemCpuGroupCollect = SystemPropertiesUtil.systemCpu();
                //系统内存用度
                SystemMemoryCollect systemMemoryCollect = SystemPropertiesUtil.getSystemMemory();
                //网络用度
                List<NetDataCollect> netDatumCollects = SystemPropertiesUtil.systemNetwork();
                SystemInfoMessage systemInfoMessage = new SystemInfoMessage();
                systemInfoMessage.setSystemMemory(systemMemoryCollect);
                systemInfoMessage.setNetInfos(netDatumCollects);
                systemInfoMessage.setSystemCpuGroup(systemCpuGroupCollect);
                systemInfoMessage.setSystemPropertiesAbstract(envAbstract);

                MessageBuffer messageBuffer = new MessageBuffer();
                messageBuffer.setDtingMessage(systemInfoMessage);
                MessageReactor.sendMessage(messageBuffer);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }, 10L, 10L, TimeUnit.SECONDS);
    }

    public static void stop() {
        COLLECTED.shutdown();
    }

}
