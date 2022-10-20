package com.dting.show.server.buffers;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.dting.common.datas.NetInfo;
import com.dting.common.datas.SystemCpuGroup;
import com.dting.common.datas.SystemMemory;
import com.dting.common.datas.SystemPropertiesAbstract;
import com.dting.show.datas.SystemInfoMessage;
import com.dting.show.server.entity.MessageCpuData;
import com.dting.show.server.entity.MessageMemoryData;
import com.dting.show.server.entity.MessageNetworkData;
import com.dting.show.server.service.MessageCpuDataService;
import com.dting.show.server.service.MessageMemoryDataService;
import com.dting.show.server.service.MessageNetworkDataService;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CPU缓冲反应堆
 *
 * @author huangfu
 * @date 2022年10月19日16:30:02
 */
public class SystemInfoDataBufferReactor extends MessageBufferReactor<SystemInfoMessage> {

    /**
     * cpu服务
     */
    private final MessageCpuDataService messageCpuDataService;

    /**
     * 内存服务
     */
    private final MessageMemoryDataService messageMemoryDataService;

    /**
     * 网卡服务
     */
    private final MessageNetworkDataService messageNetworkDataService;


    public SystemInfoDataBufferReactor(MessageCpuDataService messageCpuDataService, MessageMemoryDataService messageMemoryDataService, MessageNetworkDataService messageNetworkDataService) {
        this.messageCpuDataService = messageCpuDataService;
        this.messageMemoryDataService = messageMemoryDataService;
        this.messageNetworkDataService = messageNetworkDataService;
    }


    @Override
    public void start0(List<SystemInfoMessage> source) {

        if (CollectionUtil.isNotEmpty(source)) {
            SystemInfoGroup systemInfoGroup = new SystemInfoGroup();
            systemInfoGroup.conversion(source);
            //保存CPU数据
            messageCpuDataService.ignoreOnlyBatchSave(systemInfoGroup.getMessageCpuDataList());
            //保存内存数据
            messageMemoryDataService.ignoreOnlyBatchSave(systemInfoGroup.getMessageMemoryDataList());
            //保存网卡数据
            messageNetworkDataService.ignoreOnlyBatchSave(systemInfoGroup.getMessageNetworkDataList());
        }
    }

    @Data
    private static class SystemInfoGroup implements Serializable {
        private static final long serialVersionUID = -819067694900256939L;
        /**
         * cpu数据
         */
        private final List<MessageCpuData> messageCpuDataList = new ArrayList<>(32);
        /**
         * 内存数据
         */
        private final List<MessageMemoryData> messageMemoryDataList = new ArrayList<>(32);

        /**
         * 网卡数据
         */
        private final List<MessageNetworkData> messageNetworkDataList = new ArrayList<>(32);

        /**
         * 开始做消息的转换 并赋值给自身
         *
         * @param source 来自客户端的消息
         */
        public void conversion(List<SystemInfoMessage> source) {
            messageCpuDataList.clear();
            messageMemoryDataList.clear();
            messageNetworkDataList.clear();

            source.forEach(data -> {
                //CPU数据转换
                MessageCpuData messageCpuData = cpuDataConversion(data);
                messageCpuDataList.add(messageCpuData);
                //内存数据转换
                MessageMemoryData messageMemoryData = memoryDataConversion(data);
                messageMemoryDataList.add(messageMemoryData);
                //网卡数据转换
                MessageNetworkData messageNetworkData = networkDataConversion(data);
                messageNetworkDataList.add(messageNetworkData);
            });
        }

        /**
         * 网卡数据转换
         *
         * @param data 网卡数据
         * @return 网卡的数据
         */
        private MessageNetworkData networkDataConversion(SystemInfoMessage data) {
            List<NetInfo> netInfos = data.getNetInfos();
            MessageNetworkData messageNetworkData = new MessageNetworkData();
            //公共信息设置
            messageNetworkData.commonDataSetting(data);
            messageNetworkData.setNetworkData(JSON.toJSONString(netInfos));
            return messageNetworkData;
        }

        /**
         * 内存数据转换
         *
         * @param data 数据
         * @return 消息内存数据
         */
        private MessageMemoryData memoryDataConversion(SystemInfoMessage data) {
            //系统摘要
            SystemPropertiesAbstract systemPropertiesAbstract = data.getSystemPropertiesAbstract();
            //系统内存
            SystemMemory systemMemory = data.getSystemMemory();
            MessageMemoryData messageMemoryData = new MessageMemoryData();
            //公共信息设置
            messageMemoryData.commonDataSetting(data);
            //系统内存的总内存
            messageMemoryData.setTotalMemory(systemMemory.getTotalMemory());
            //系统内存的使用内存
            messageMemoryData.setUseMemory(systemMemory.getUseMemory());
            //jvm总内存
            Long jvmTotalMemory = systemPropertiesAbstract.getJvmTotalMemory();
            messageMemoryData.setJvmTotalMemory(jvmTotalMemory);
            //jvm已经使用的内存
            messageMemoryData.setJvmUseMemory(jvmTotalMemory - systemPropertiesAbstract.getJvmRemainingMemory());
            //交换区总内存
            messageMemoryData.setTotalSwap(systemMemory.getTotalSwap());
            //交换区已经使用的大小
            messageMemoryData.setUseSwap(systemMemory.getUseSwap());
            return messageMemoryData;
        }


        /**
         * cpu数据转换
         *
         * @param systemInfoMessage cpu的原始数据
         * @return cpu的格式化数据
         */
        public MessageCpuData cpuDataConversion(SystemInfoMessage systemInfoMessage) {
            SystemCpuGroup systemCpuGroup = systemInfoMessage.getSystemCpuGroup();
            MessageCpuData messageCpuData = new MessageCpuData();
            //公共信息设置
            messageCpuData.commonDataSetting(systemInfoMessage);
            //每一个CPU的信息
            messageCpuData.setCpuInfo(JSON.toJSONString(systemCpuGroup.getSystemCpus()));
            //总使用比率
            messageCpuData.setTotalUse(systemCpuGroup.getCpuTotal());
            //用户使用比率
            messageCpuData.setUserUes(systemCpuGroup.getCpuUserUse());
            //系统使用比率
            messageCpuData.setSystemUes(systemCpuGroup.getCpuSystemUse());
            //等待率
            messageCpuData.setWait(systemCpuGroup.getCpuWait());
            //错误率
            messageCpuData.setError(systemCpuGroup.getCpuError());
            //空闲率
            messageCpuData.setIdle(systemCpuGroup.getCpuIdle());
            return messageCpuData;
        }


    }


}
