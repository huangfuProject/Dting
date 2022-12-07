package com.dting.sdk.apps.services;

import cn.hutool.core.collection.CollUtil;
import com.dting.thread.pool.DtingThreadPoolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * *************************************************<br/>
 * 持续计算<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/7 15:40
 */
public class ContinuousCalculationService {

    private final static int CORE_CONT = Runtime.getRuntime().availableProcessors();


    private final static DtingThreadPoolExecutor CONTINUOUS_CALCULATION_POOL
            = new DtingThreadPoolExecutor(CORE_CONT, CORE_CONT * 4, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(),new ThreadPoolExecutor.CallerRunsPolicy(), "ContinuousCalculation");

    private final static Random RANDOM = new Random();

    public void calculation(){
        CONTINUOUS_CALCULATION_POOL.execute(() ->{
            try {
                List<Integer> numList = new ArrayList<>(8);

                for (int i = 0; i < 10000; i++) {
                    int nextIntCopy = RANDOM.nextInt(10000);
                    int nextInt = RANDOM.nextInt(10000);
                    if(nextInt % nextIntCopy == 0) {
                        numList.add(nextInt);
                    }
                }

                if(CollUtil.isNotEmpty(numList)) {
                    Integer a = numList.get(RANDOM.nextInt(numList.size()));
                    Integer b = numList.get(RANDOM.nextInt(numList.size()));
                    System.out.println("计算结果为：" + a/b);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
