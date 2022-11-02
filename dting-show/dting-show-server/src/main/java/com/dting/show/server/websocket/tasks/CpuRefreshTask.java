package com.dting.show.server.websocket.tasks;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.dting.show.server.constant.RedisKeyUtil;
import com.dting.show.server.utils.SpringUtil;
import com.dting.show.server.websocket.MessageEntity;
import com.dting.show.server.websocket.MessageWebSocket;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * *************************************************<br/>
 * CPU刷新数据<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/30 12:53
 */
@SuppressWarnings("all")
public class CpuRefreshTask implements Runnable {

    /**
     * 会话id
     */
    private final String sessionId;

    /**
     * 前端通讯器
     */
    private final MessageWebSocket messageWebSocket;

    /**
     * redis的操作工具类
     */
    private final StringRedisTemplate redisTemplate;

    public CpuRefreshTask(String sessionId, MessageWebSocket messageWebSocket) {
        this.sessionId = sessionId;
        this.messageWebSocket = messageWebSocket;
        this.redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
    }

    @Override
    public void run() {
        //对该主键进行续期
        this.redisTemplate.expire(RedisKeyUtil.sessionActiveKeyFormat(sessionId), 60, TimeUnit.SECONDS);
        //获取对应的CPU数据
        ListOperations<String, String> operations = this.redisTemplate.opsForList();
        String cpuKey = RedisKeyUtil.dtingCpuCacheFormat(sessionId);
        List<String> CpuDataList = operations.rightPop(cpuKey, operations.size(cpuKey));
        if (CollectionUtil.isNotEmpty(CpuDataList)) {
            for (String memoryData : CpuDataList) {
                if (StrUtil.isNotEmpty(memoryData)) {
                    MessageEntity messageEntity = new MessageEntity(MessageEntity.CPU_DATA_TYPE, memoryData);
                    //发送数据
                    this.messageWebSocket.sendMessage(JSON.toJSONString(messageEntity));
                }
            }
        }

    }
}
