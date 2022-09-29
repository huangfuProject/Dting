package com.dting.thread.rejected;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 内部封装的拒绝策略，模板方法，埋点拒绝方法的执行
 *
 * @author huangfu
 * @date 2022年9月29日10:33:58
 */
public class DtingRejectedExecutionHandler implements RejectedExecutionHandler {

    private final RejectedExecutionHandler rejectedExecutionHandler;

    public DtingRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            rejectedExecutionHandler.rejectedExecution(r, executor);
        } finally {
            System.out.println("拒绝策略");
        }
    }
}
