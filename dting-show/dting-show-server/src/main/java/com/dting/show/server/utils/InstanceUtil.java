package com.dting.show.server.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.dting.show.server.constant.RedisKeyUtil;
import com.dting.show.server.dto.InstanceData;
import com.dting.show.server.entity.DtingEnv;
import com.dting.show.server.entity.DtingInstance;
import com.dting.show.server.entity.DtingServer;
import com.dting.show.server.enums.InstanceState;
import com.dting.show.server.service.DtingEnvService;
import com.dting.show.server.service.DtingInstanceService;
import com.dting.show.server.service.DtingServerService;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * *************************************************<br/>
 * 实例工具<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 9:54
 */
@SuppressWarnings("all")
public class InstanceUtil {


    /**
     * 查询缓存的实例
     *
     * @param envName      环境名称
     * @param serverName   服务名称
     * @param instanceName 实例名称
     * @return 实例数据
     */
    public static InstanceData findAndCacheInstance(String envName, String serverName, String instanceName) {
        DtingEnvService dtingEnvService = SpringUtil.getBean(DtingEnvService.class);
        DtingServerService dtingServerService = SpringUtil.getBean(DtingServerService.class);
        DtingInstanceService dtingInstanceService = SpringUtil.getBean(DtingInstanceService.class);
        StringRedisTemplate redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        String dtingInstanceCacheFormat = RedisKeyUtil.dtingInstanceCacheFormat(envName, serverName, instanceName);
        //查询redis中是否存在
        if (!redisTemplate.hasKey(dtingInstanceCacheFormat)) {
            //查询数据库是否存在
            DtingEnv env = dtingEnvService.findByName(envName);
            if (env == null) {
                return null;

            }
            //查询服务
            Integer envId = env.getId();
            DtingServer server = dtingServerService.findByEnvIdAndServerName(envId, serverName);
            if (server == null) {
                return null;
            }

            Integer serverId = server.getId();
            DtingInstance instance = dtingInstanceService.findByServerIdAndInstanceName(serverId, instanceName);
            if (instance == null) {
                return null;
            }

            InstanceData instanceData = new InstanceData();
            instanceData.setDtingEnv(env);
            instanceData.setDtingServer(server);
            instanceData.setDtingInstance(instance);
            redisTemplate.opsForValue().set(dtingInstanceCacheFormat, JSON.toJSONString(instanceData), 1, TimeUnit.HOURS);
            return instanceData;
        } else {
            String instanceDataStr = redisTemplate.opsForValue().get(dtingInstanceCacheFormat);
            if (StrUtil.isNotBlank(instanceDataStr)) {
                return JSON.parseObject(instanceDataStr, InstanceData.class);
            }
        }
        return null;
    }

    /**
     * 查询 保存 缓存实例数据
     *
     * @param envName      环境名称
     * @param serverName   服务名称
     * @param instanceName 实例名称
     * @param timeout      超时时间
     * @return 实例数据
     */
    public static InstanceData findAndSaveAndCache(String envName, String serverName, String instanceName, Long timeout) {
        DtingEnvService dtingEnvService = SpringUtil.getBean(DtingEnvService.class);
        DtingServerService dtingServerService = SpringUtil.getBean(DtingServerService.class);
        DtingInstanceService dtingInstanceService = SpringUtil.getBean(DtingInstanceService.class);
        StringRedisTemplate redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);

        String dtingInstanceCacheFormat = RedisKeyUtil.dtingInstanceCacheFormat(envName, serverName, instanceName);
        //查询redis中是否存在
        if (!redisTemplate.hasKey(dtingInstanceCacheFormat)) {
            //查询数据库是否存在
            DtingEnv env = dtingEnvService.findByName(envName);
            if (env == null) {
                //保存环境信息
                env = new DtingEnv();
                env.setCreateDate(System.currentTimeMillis());
                env.setEnvName(envName);
                dtingEnvService.save(env);

            }
            //查询服务
            Integer envId = env.getId();
            DtingServer server = dtingServerService.findByEnvIdAndServerName(envId, serverName);
            if (server == null) {
                server = new DtingServer();
                server.setServerName(serverName);
                server.setEnvId(envId);
                server.setCreateDate(System.currentTimeMillis());
                dtingServerService.save(server);
            }

            Integer serverId = server.getId();
            DtingInstance instance = dtingInstanceService.findByServerIdAndInstanceName(serverId, instanceName);
            if (instance == null) {
                instance = new DtingInstance();
                instance.setInstanceName(instanceName);
                instance.setState(InstanceState.UP.getCode());
                instance.setCreateDate(System.currentTimeMillis());
                instance.setTimeout(timeout == null ? TimeUnit.DAYS.toMillis(1) : timeout);
                instance.setServerId(serverId);
                instance.setLastUpdateTime(System.currentTimeMillis());
                dtingInstanceService.save(instance);
            }

            InstanceData instanceData = new InstanceData();
            instanceData.setDtingEnv(env);
            instanceData.setDtingServer(server);
            instanceData.setDtingInstance(instance);
            redisTemplate.opsForValue().set(dtingInstanceCacheFormat, JSON.toJSONString(instanceData), 1, TimeUnit.HOURS);
            return instanceData;
        }

        String instanceDataStr = redisTemplate.opsForValue().get(dtingInstanceCacheFormat);
        //续约
        redisTemplate.expire(dtingInstanceCacheFormat, 1, TimeUnit.HOURS);
        return JSON.parseObject(instanceDataStr, InstanceData.class);
    }
}
