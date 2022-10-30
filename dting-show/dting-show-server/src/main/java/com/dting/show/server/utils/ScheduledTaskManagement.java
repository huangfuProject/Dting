package com.dting.show.server.utils;

import io.netty.util.HashedWheelTimer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;


/**
 * 定时任务管理
 *
 * @author huangfu
 * @date 2022年10月30日 10点00分
 */
public class ScheduledTaskManagement {

    /**
     * HASHED_WHEEL_TIMER:工作轮盘 <br/>
     * ThreadFactory：创建work线程 <br/>
     * tickDuration:每个刻度的时间 <br/>
     * ticsPerWheel:轮盘一圈大小 <br/>
     * maxPendingTimeouts:最大等待处理超时 <br/>
     */
    private static final HashedWheelTimer HASHED_WHEEL_TIMER = new HashedWheelTimer(r -> new Thread(r, "RefreshMonitoringJob"), 200, TimeUnit.MILLISECONDS, 32, true, 0);


    /**
     * 增加一个任务 向调度盘
     *
     * @param timerTask       任务
     * @param triggerNextTime 开始执行的时间
     */
    public static void addJob(TimerTask timerTask, long triggerNextTime) {
        //设置这个的根部原因是因为保证任务的抛出在事务提交动作完成之后抛出
        long time = triggerNextTime - System.currentTimeMillis();
        HASHED_WHEEL_TIMER.newTimeout(timerTask, TimeUnit.MILLISECONDS.toNanos(time), TimeUnit.NANOSECONDS);
    }
}
