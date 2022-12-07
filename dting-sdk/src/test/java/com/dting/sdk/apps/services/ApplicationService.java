package com.dting.sdk.apps.services;

import com.dting.thread.pool.DtingThreadPoolExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * *************************************************<br/>
 * 应用服务<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/7 15:37
 */
public class ApplicationService {
    private final static int CORE_CONT = Runtime.getRuntime().availableProcessors();

    private final ContinuousCalculationService continuousCalculationService = new ContinuousCalculationService();
    /**
     * 主线程池
     */
    private final static DtingThreadPoolExecutor MAIN_POOL = new DtingThreadPoolExecutor(CORE_CONT, CORE_CONT * 25, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024), "main-pool");

    public void app(){
        MAIN_POOL.execute(continuousCalculationService::calculation);

    }

}
