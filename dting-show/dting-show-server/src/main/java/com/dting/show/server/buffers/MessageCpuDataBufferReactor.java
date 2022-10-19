package com.dting.show.server.buffers;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.dting.common.datas.SystemCpuGroup;
import com.dting.show.datas.SystemInfoMessage;
import com.dting.show.server.entity.MessageCpuData;
import com.dting.show.server.service.MessageCpuDataService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CPU缓冲反应堆
 *
 * @author huangfu
 * @date 2022年10月19日16:30:02
 */
public class MessageCpuDataBufferReactor extends MessageBufferReactor<SystemInfoMessage> {

    private final MessageCpuDataService messageCpuDataService;


    public MessageCpuDataBufferReactor(MessageCpuDataService messageCpuDataService) {
        this.messageCpuDataService = messageCpuDataService;
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
        //每一个CPU的信息
        messageCpuData.setCpuInfo(JSON.toJSONString(systemCpuGroup.getSystemCpus()));
        //消息来源
        messageCpuData.setMessageIp(systemInfoMessage.getMessageSourceAddress());
        //消息标签
        messageCpuData.setMessageTag(systemInfoMessage.getMessageTag());
        //消息的唯一值
        messageCpuData.setUnique(systemInfoMessage.getUnique());
        //消息采集时间
        messageCpuData.setCollectTime(System.nanoTime());
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

    @Override
    public void start0(List<SystemInfoMessage> source) {

        if(CollectionUtil.isNotEmpty(source)) {
            List<MessageCpuData> messageCpuDataList = source.stream().map(this::cpuDataConversion).collect(Collectors.toList());
            messageCpuDataService.ignoreOnlyBatchSave(messageCpuDataList);
        }
    }
}
