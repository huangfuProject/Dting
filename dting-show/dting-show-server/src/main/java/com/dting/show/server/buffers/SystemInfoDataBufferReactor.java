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
import com.dting.show.server.entity.MessageCpuSnapshot;
import com.dting.show.server.entity.MessageMemorySnapshot;
import com.dting.show.server.entity.MessageNetworkSnapshot;
import com.dting.show.server.entity.NetworkDetailedSnapshot;
import com.dting.show.server.service.MessageCpuSnapshotService;
import com.dting.show.server.service.MessageMemorySnapshotService;
import com.dting.show.server.service.NetworkDetailedSnapshotService;
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
    private final MessageCpuSnapshotService messageCpuSnapshotService;

    /**
     * 内存服务
     */
    private final MessageMemorySnapshotService messageMemorySnapshotService;

    /**
     * 网卡服务
     */
    private final MessageNetworkDataService messageNetworkDataService;

    /**
     * 网卡的详细数据服务
     */
    private final NetworkDetailedSnapshotService networkDetailedSnapshotService;


    public SystemInfoDataBufferReactor(MessageCpuSnapshotService messageCpuSnapshotService, MessageMemorySnapshotService messageMemorySnapshotService, MessageNetworkDataService messageNetworkDataService, NetworkDetailedSnapshotService networkDetailedSnapshotService) {
        this.messageCpuSnapshotService = messageCpuSnapshotService;
        this.messageMemorySnapshotService = messageMemorySnapshotService;
        this.messageNetworkDataService = messageNetworkDataService;
        this.networkDetailedSnapshotService = networkDetailedSnapshotService;
    }


    @Override
    public void start0(List<SystemInfoMessage> source) {

        if (CollectionUtil.isNotEmpty(source)) {
            SystemInfoGroup systemInfoGroup = new SystemInfoGroup();
            systemInfoGroup.conversion(source);
            //保存CPU数据
            messageCpuSnapshotService.ignoreOnlyBatchSave(systemInfoGroup.getMessageCpuSnapshotList());
            //保存内存数据
            messageMemorySnapshotService.ignoreOnlyBatchSave(systemInfoGroup.getMessageMemorySnapshotList());
            //保存网卡数据
            messageNetworkDataService.ignoreOnlyBatchSave(systemInfoGroup.getMessageNetworkSnapshotList());
            //保存网卡的详细数据
            networkDetailedSnapshotService.batchSave(systemInfoGroup.getNetworkDetailedSnapshotList());
        }
    }

    @Data
    private static class SystemInfoGroup implements Serializable {
        private static final long serialVersionUID = -819067694900256939L;
        /**
         * cpu数据
         */
        private final List<MessageCpuSnapshot> messageCpuSnapshotList = new ArrayList<>(32);
        /**
         * 内存数据
         */
        private final List<MessageMemorySnapshot> messageMemorySnapshotList = new ArrayList<>(32);

        /**
         * 网卡数据
         */
        private final List<MessageNetworkSnapshot> messageNetworkSnapshotList = new ArrayList<>(32);

        /**
         * 每一块网卡的具体读写数据
         */
        private final List<NetworkDetailedSnapshot> networkDetailedSnapshotList = new ArrayList<>(32);

        /**
         * 开始做消息的转换 并赋值给自身
         *
         * @param source 来自客户端的消息
         */
        public void conversion(List<SystemInfoMessage> source) {
            messageCpuSnapshotList.clear();
            messageMemorySnapshotList.clear();
            messageNetworkSnapshotList.clear();

            source.forEach(data -> {
                //CPU数据转换
                MessageCpuSnapshot messageCpuSnapshot = cpuDataConversion(data);
                messageCpuSnapshotList.add(messageCpuSnapshot);
                //内存数据转换
                MessageMemorySnapshot messageMemorySnapshot = memoryDataConversion(data);
                messageMemorySnapshotList.add(messageMemorySnapshot);
                //网卡数据转换
                MessageNetworkSnapshot messageNetworkSnapshot = networkDataConversion(data);
                messageNetworkSnapshotList.add(messageNetworkSnapshot);
                //网卡子表数据转换
                List<NetDataCollect> netDatumCollects = data.getNetInfos();
                List<NetworkDetailedSnapshot> networkDetailedSnapshotListTmp = netDatumCollects.stream().map(info -> {
                    NetworkDetailedSnapshot networkDetailedSnapshot = new NetworkDetailedSnapshot();
                    BeanUtil.copyProperties(info, networkDetailedSnapshot);
                    networkDetailedSnapshot.setNetworkDataKey(messageNetworkSnapshot.getNetworkDataKey());
                    return networkDetailedSnapshot;
                }).collect(Collectors.toList());
                //追加网卡子表转换
                networkDetailedSnapshotList.addAll(networkDetailedSnapshotListTmp);
            });
        }

        /**
         * 网卡数据转换
         *
         * @param data 网卡数据
         * @return 网卡的数据
         */
        private MessageNetworkSnapshot networkDataConversion(SystemInfoMessage data) {
            MessageNetworkSnapshot messageNetworkSnapshot = new MessageNetworkSnapshot();
            //公共信息设置
            messageNetworkSnapshot.commonDataSetting(data);
            //生成网卡组
            String groupKey = IdUtil.simpleUUID();
            messageNetworkSnapshot.setNetworkDataKey(groupKey);
            return messageNetworkSnapshot;
        }

        /**
         * 内存数据转换
         *
         * @param data 数据
         * @return 消息内存数据
         */
        private MessageMemorySnapshot memoryDataConversion(SystemInfoMessage data) {
            //系统摘要
            SystemPropertiesAbstractCollect systemPropertiesAbstractCollect = data.getSystemPropertiesAbstract();
            //系统内存
            SystemMemoryCollect systemMemoryCollect = data.getSystemMemory();
            MessageMemorySnapshot messageMemorySnapshot = new MessageMemorySnapshot();
            //公共信息设置
            messageMemorySnapshot.commonDataSetting(data);
            //系统内存的总内存
            messageMemorySnapshot.setTotalMemory(systemMemoryCollect.getTotalMemory());
            //系统内存的使用内存
            messageMemorySnapshot.setUseMemory(systemMemoryCollect.getUseMemory());
            //jvm总内存
            Long jvmTotalMemory = systemPropertiesAbstractCollect.getJvmTotalMemory();
            messageMemorySnapshot.setJvmTotalMemory(jvmTotalMemory);
            //jvm已经使用的内存
            messageMemorySnapshot.setJvmUseMemory(jvmTotalMemory - systemPropertiesAbstractCollect.getJvmRemainingMemory());
            //交换区总内存
            messageMemorySnapshot.setTotalSwap(systemMemoryCollect.getTotalSwap());
            //交换区已经使用的大小
            messageMemorySnapshot.setUseSwap(systemMemoryCollect.getUseSwap());
            return messageMemorySnapshot;
        }


        /**
         * cpu数据转换
         *
         * @param systemInfoMessage cpu的原始数据
         * @return cpu的格式化数据
         */
        public MessageCpuSnapshot cpuDataConversion(SystemInfoMessage systemInfoMessage) {
            SystemCpuGroupCollectCollect systemCpuGroupCollect = systemInfoMessage.getSystemCpuGroup();
            MessageCpuSnapshot messageCpuSnapshot = new MessageCpuSnapshot();
            //公共信息设置
            messageCpuSnapshot.commonDataSetting(systemInfoMessage);
            //每一个CPU的信息
            messageCpuSnapshot.setCpuInfo(JSON.toJSONString(systemCpuGroupCollect.getSystemCpus()));
            //总使用比率
            messageCpuSnapshot.setTotalUse(systemCpuGroupCollect.getCpuTotal());
            //用户使用比率
            messageCpuSnapshot.setUserUes(systemCpuGroupCollect.getCpuUserUse());
            //系统使用比率
            messageCpuSnapshot.setSystemUes(systemCpuGroupCollect.getCpuSystemUse());
            //等待率
            messageCpuSnapshot.setWait(systemCpuGroupCollect.getCpuWait());
            //错误率
            messageCpuSnapshot.setError(systemCpuGroupCollect.getCpuError());
            //空闲率
            messageCpuSnapshot.setIdle(systemCpuGroupCollect.getCpuIdle());
            return messageCpuSnapshot;
        }


    }


}
