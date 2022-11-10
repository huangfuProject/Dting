package com.dting.sdk.processing;

import com.dting.cache.DtingThreadPoolCache;
import com.dting.message.common.handlers.DtingSimpleChannelInboundHandler;
import com.dting.show.datas.ThreadPoolDetailedConfigMessage;
import com.dting.thread.pool.DtingThreadPoolExecutor;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.TimeUnit;

/**
 * *************************************************<br/>
 * 线程池的更新处理器<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/6 15:56
 */
public class ThreadConfigUpdateProcessing extends DtingSimpleChannelInboundHandler<ThreadPoolDetailedConfigMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ThreadPoolDetailedConfigMessage threadPoolDetailedConfigMessage) throws Exception {
        System.out.println("发现与远程配置不同，远程控制在中心发起修改操作 ：" + threadPoolDetailedConfigMessage);
        DtingThreadPoolExecutor threadPool = DtingThreadPoolCache.getThreadPool(threadPoolDetailedConfigMessage.getPoolName());
        if (threadPool != null) {
            //核心数
            int corePoolSize = threadPool.getCorePoolSize();
            //最大线程数
            int maximumPoolSize = threadPool.getMaximumPoolSize();
            //睡眠数
            long keepAliveTime = threadPool.getKeepAliveTime(TimeUnit.MILLISECONDS);
            //拒绝策略
            String rejectedExecutionHandlerName = threadPool.getRejectedExecutionHandler().getClass().getName();

            //谁不一样 改谁
            //服务端核心数
            int coreSizeServer = threadPoolDetailedConfigMessage.getCoreSize();
            if (corePoolSize != coreSizeServer) {
                threadPool.setCorePoolSize(coreSizeServer);
            }
            //服务端最大核心数
            int maxSizeServer = threadPoolDetailedConfigMessage.getMaxSize();
            if (maximumPoolSize != maxSizeServer) {
                threadPool.setMaximumPoolSize(maxSizeServer);
            }
            //服务端睡眠
            Long keepAliveTimeServer = threadPoolDetailedConfigMessage.getKeepAliveTime();
            if (keepAliveTime != keepAliveTimeServer) {
                threadPool.setKeepAliveTime(keepAliveTimeServer, TimeUnit.MILLISECONDS);
            }

            String rejectHandlerName = threadPoolDetailedConfigMessage.getRejectHandlerName();
            if (!rejectedExecutionHandlerName.equals(rejectHandlerName)) {
                try {
                    RejectedExecutionHandler newInstance = (RejectedExecutionHandler) Class.forName(rejectHandlerName).newInstance();
                    threadPool.setRejectedExecutionHandler(newInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
