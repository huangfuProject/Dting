package com.dting.show.server.buffers;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.dting.message.common.Communication;
import com.dting.show.datas.ThreadPoolDetailedConfigMessage;
import com.dting.show.server.entity.ThreadPoolConfig;
import com.dting.show.server.service.ThreadPoolConfigService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * *************************************************<br/>
 * 针对线程池的配置信息进行的一个比对修改操作<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/6 16:06
 */
@Slf4j
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
                        if (paramCheck(coreCount, maxCount, keepAliveTime)) {
                            //数据不一致 发送一个修改任务
                            ThreadPoolDetailedConfigMessage newThreadPoolDetailedConfigMessage = new ThreadPoolDetailedConfigMessage();
                            BeanUtil.copyProperties(config, newThreadPoolDetailedConfigMessage);
                            newThreadPoolDetailedConfigMessage.setPoolName(threadPoolName);
                            newThreadPoolDetailedConfigMessage.setCoreSize(coreCount);
                            newThreadPoolDetailedConfigMessage.setMaxSize(maxCount);
                            Communication communication = source.getCommunication();
                            communication.asyncSendMessage(newThreadPoolDetailedConfigMessage);
                        } else {
                            log.error("环境：{}，服务：{}，实例：{}，线程池：{} 参数不合法，要求 [(核心并发 > 0) && (最大并发 > 0) && (最大并发 > 核心并发) && (空闲超时时间 > 0)]," +
                                    "实际配置为核心并发：{},最大并发：{},空闲超时时间：{}，线程池远程通讯失败！！请修改该线程池的参数信息", serverEnv, serverKey, instanceKey, threadPoolName, coreCount, maxCount, keepAliveTime);
                        }

                    }
                    //对比与数据库中的ip是否一致，不一致就修改
                    String messageIp = config.getMessageIp();
                    String messageSourceAddress = threadPoolDetailedConfigMessage.getMessageSourceAddress();

                    if (!messageIp.equals(messageSourceAddress)) {
                        ThreadPoolConfig threadPoolConfigUpdate = new ThreadPoolConfig();
                        threadPoolConfigUpdate.setId(config.getId());
                        threadPoolConfigUpdate.setMessageIp(messageSourceAddress);
                        //修改ip地址
                        threadPoolConfigService.updateById(threadPoolConfigUpdate);
                    }
                }
            });
        }

    }

    /**
     * 线程池参数校验
     *
     * @param coreSize      核心参数
     * @param maxSize       最大核心
     * @param keepAliveTime 空闲时间
     * @return 是否合法
     */
    private boolean paramCheck(int coreSize, int maxSize, long keepAliveTime) {
        return coreSize >= 0 && maxSize > 0 && maxSize >= coreSize && keepAliveTime >= 0;
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
