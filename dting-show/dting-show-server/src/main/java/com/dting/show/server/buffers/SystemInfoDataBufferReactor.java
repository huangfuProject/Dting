package com.dting.show.server.buffers;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.dting.common.datas.NetDataCollect;
import com.dting.common.datas.SystemCpuGroupCollectCollect;
import com.dting.common.datas.SystemMemoryCollect;
import com.dting.common.datas.SystemPropertiesAbstractCollect;
import com.dting.show.datas.SystemInfoMessage;
import com.dting.show.server.entity.MessageCpuData;
import com.dting.show.server.entity.MessageMemoryData;
import com.dting.show.server.entity.NetworkInfo;
import com.dting.show.server.entity.MessageNetworkData;
import com.dting.show.server.service.MessageCpuDataService;
import com.dting.show.server.service.MessageMemoryDataService;
import com.dting.show.server.service.NetworkInfoService;
import com.dting.show.server.service.MessageNetworkDataService;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 网卡的详细数据服务
     */
    private final NetworkInfoService networkInfoService;


    public SystemInfoDataBufferReactor(MessageCpuDataService messageCpuDataService, MessageMemoryDataService messageMemoryDataService, MessageNetworkDataService messageNetworkDataService, NetworkInfoService networkInfoService) {
        this.messageCpuDataService = messageCpuDataService;
        this.messageMemoryDataService = messageMemoryDataService;
        this.messageNetworkDataService = messageNetworkDataService;
        this.networkInfoService = networkInfoService;
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
            //保存网卡的详细数据
            networkInfoService.batchSave(systemInfoGroup.getNetworkInfoList());
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
         * 每一块网卡的具体读写数据
         */
        private final List<NetworkInfo> networkInfoList = new ArrayList<>(32);

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
                //网卡子表数据转换
                List<NetDataCollect> netDatumCollects = data.getNetInfos();
                List<NetworkInfo> networkInfoListTmp = netDatumCollects.stream().map(info -> {
                    NetworkInfo networkInfo = new NetworkInfo();
                    BeanUtil.copyProperties(info, networkInfo);
                    networkInfo.setNetworkDataKey(messageNetworkData.getNetworkDataKey());
                    return networkInfo;
                }).collect(Collectors.toList());
                //追加网卡子表转换
                networkInfoList.addAll(networkInfoListTmp);
            });
        }

        /**
         * 网卡数据转换
         *
         * @param data 网卡数据
         * @return 网卡的数据
         */
        private MessageNetworkData networkDataConversion(SystemInfoMessage data) {
            MessageNetworkData messageNetworkData = new MessageNetworkData();
            //公共信息设置
            messageNetworkData.commonDataSetting(data);
            //生成网卡组
            String groupKey = IdUtil.simpleUUID();
            messageNetworkData.setNetworkDataKey(groupKey);
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
            SystemPropertiesAbstractCollect systemPropertiesAbstractCollect = data.getSystemPropertiesAbstract();
            //系统内存
            SystemMemoryCollect systemMemoryCollect = data.getSystemMemory();
            MessageMemoryData messageMemoryData = new MessageMemoryData();
            //公共信息设置
            messageMemoryData.commonDataSetting(data);
            //系统内存的总内存
            messageMemoryData.setTotalMemory(systemMemoryCollect.getTotalMemory());
            //系统内存的使用内存
            messageMemoryData.setUseMemory(systemMemoryCollect.getUseMemory());
            //jvm总内存
            Long jvmTotalMemory = systemPropertiesAbstractCollect.getJvmTotalMemory();
            messageMemoryData.setJvmTotalMemory(jvmTotalMemory);
            //jvm已经使用的内存
            messageMemoryData.setJvmUseMemory(jvmTotalMemory - systemPropertiesAbstractCollect.getJvmRemainingMemory());
            //交换区总内存
            messageMemoryData.setTotalSwap(systemMemoryCollect.getTotalSwap());
            //交换区已经使用的大小
            messageMemoryData.setUseSwap(systemMemoryCollect.getUseSwap());
            return messageMemoryData;
        }


        /**
         * cpu数据转换
         *
         * @param systemInfoMessage cpu的原始数据
         * @return cpu的格式化数据
         */
        public MessageCpuData cpuDataConversion(SystemInfoMessage systemInfoMessage) {
            SystemCpuGroupCollectCollect systemCpuGroupCollect = systemInfoMessage.getSystemCpuGroup();
            MessageCpuData messageCpuData = new MessageCpuData();
            //公共信息设置
            messageCpuData.commonDataSetting(systemInfoMessage);
            //每一个CPU的信息
            messageCpuData.setCpuInfo(JSON.toJSONString(systemCpuGroupCollect.getSystemCpus()));
            //总使用比率
            messageCpuData.setTotalUse(systemCpuGroupCollect.getCpuTotal());
            //用户使用比率
            messageCpuData.setUserUes(systemCpuGroupCollect.getCpuUserUse());
            //系统使用比率
            messageCpuData.setSystemUes(systemCpuGroupCollect.getCpuSystemUse());
            //等待率
            messageCpuData.setWait(systemCpuGroupCollect.getCpuWait());
            //错误率
            messageCpuData.setError(systemCpuGroupCollect.getCpuError());
            //空闲率
            messageCpuData.setIdle(systemCpuGroupCollect.getCpuIdle());
            return messageCpuData;
        }


    }


}
