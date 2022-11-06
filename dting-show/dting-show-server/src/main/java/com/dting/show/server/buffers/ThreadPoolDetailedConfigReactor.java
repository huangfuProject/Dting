package com.dting.show.server.buffers;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.dting.message.common.Communication;
import com.dting.show.datas.ThreadPoolDetailedConfigMessage;
import com.dting.show.server.entity.ThreadPoolConfig;
import com.dting.show.server.service.ThreadPoolConfigService;

import java.util.List;

/**
 * *************************************************<br/>
 * 针对线程池的配置信息进行的一个比对修改操作<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/6 16:06
 */
public class ThreadPoolDetailedConfigReactor extends MessageBufferReactor<ThreadPoolDetailedConfigReactor.ReactionMaterial> {

    private final ThreadPoolConfigService threadPoolConfigService;

    public ThreadPoolDetailedConfigReactor(ThreadPoolConfigService threadPoolConfigService) {
        this.threadPoolConfigService = threadPoolConfigService;
    }


    @Override
    public void start0(List<ReactionMaterial> sources) {
        if (CollUtil.isNotEmpty(sources)) {
            sources.forEach(source -> {
                ThreadPoolDetailedConfigMessage threadPoolDetailedConfigMessage = source.getThreadPoolDetailedConfigMessage();
                String serverEnv = threadPoolDetailedConfigMessage.getServerEnv();
                String serverKey = threadPoolDetailedConfigMessage.getServerKey();
                String instanceKey = threadPoolDetailedConfigMessage.getInstanceKey();
                String poolName = threadPoolDetailedConfigMessage.getPoolName();
                String threadPoolNameGroup = String.format("%s:%s:%s:%s", serverEnv, serverKey, instanceKey, poolName);
                //查询该线程池
                ThreadPoolConfig config = threadPoolConfigService.findThreadPoolConfigByThreadPoolGroupName(threadPoolNameGroup);
                if (config == null) {
                    //保存线程池
                    ThreadPoolConfig configNew = new ThreadPoolConfig();
                    BeanUtil.copyProperties(threadPoolDetailedConfigMessage, configNew);
                    configNew.setThreadPoolGroupName(threadPoolNameGroup);
                    configNew.setThreadPoolName(poolName);
                    configNew.setCoreCount(threadPoolDetailedConfigMessage.getCoreSize());
                    configNew.setMaxCount(threadPoolDetailedConfigMessage.getMaxSize());
                    configNew.commonDataSetting(threadPoolDetailedConfigMessage);
                    threadPoolConfigService.save(configNew);
                } else {
                    String threadPoolName = config.getThreadPoolName();
                    Integer maxCount = config.getMaxCount();
                    Integer coreCount = config.getCoreCount();
                    String rejectHandlerName = config.getRejectHandlerName();
                    Long keepAliveTime = config.getKeepAliveTime();
                    String queueTypeName = config.getQueueTypeName();
                    //数据库存储的
                    String dbThreadPoolConfigData = String.format("%s:%s:%s:%s:%s:%s", threadPoolName, maxCount, coreCount, keepAliveTime, rejectHandlerName, queueTypeName);
                    //远程客户端的
                    String remThreadPoolConfigData = String.format("%s:%s:%s:%s:%s:%s", threadPoolDetailedConfigMessage.getPoolName(), threadPoolDetailedConfigMessage.getMaxSize(), threadPoolDetailedConfigMessage.getCoreSize(), threadPoolDetailedConfigMessage.getKeepAliveTime(), threadPoolDetailedConfigMessage.getRejectHandlerName(), threadPoolDetailedConfigMessage.getQueueTypeName());

                    //查看数据是否一致
                    if (!dbThreadPoolConfigData.equals(remThreadPoolConfigData)) {
                        //数据不一致 发送一个修改任务
                        ThreadPoolDetailedConfigMessage newThreadPoolDetailedConfigMessage = new ThreadPoolDetailedConfigMessage();
                        BeanUtil.copyProperties(config, newThreadPoolDetailedConfigMessage);
                        newThreadPoolDetailedConfigMessage.setPoolName(threadPoolName);
                        newThreadPoolDetailedConfigMessage.setCoreSize(coreCount);
                        newThreadPoolDetailedConfigMessage.setMaxSize(maxCount);
                        Communication communication = source.getCommunication();
                        communication.asyncSendMessage(newThreadPoolDetailedConfigMessage);
                    }
                }
            });
        }

    }

    public static class ReactionMaterial {
        /**
         * 通讯器
         */
        private final Communication communication;

        /**
         * 传递的线程池的信息
         */
        private final ThreadPoolDetailedConfigMessage threadPoolDetailedConfigMessage;

        public ReactionMaterial(Communication communication, ThreadPoolDetailedConfigMessage threadPoolDetailedConfigMessage) {
            this.communication = communication;
            this.threadPoolDetailedConfigMessage = threadPoolDetailedConfigMessage;
        }

        public Communication getCommunication() {
            return communication;
        }

        public ThreadPoolDetailedConfigMessage getThreadPoolDetailedConfigMessage() {
            return threadPoolDetailedConfigMessage;
        }
    }
}
