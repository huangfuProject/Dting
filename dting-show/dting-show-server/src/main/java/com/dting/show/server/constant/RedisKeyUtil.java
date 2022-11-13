package com.dting.show.server.constant;

/**
 * *************************************************<br/>
 * redis中需要用的key<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/30 10:31
 */
public class RedisKeyUtil {
    private final static String WEB_SOCKET_SESSION_ACTIVE_KEY = "dting:websocket:session.%s";

    /**
     * 内存的缓存数据
     */
    private final static String DTING_MEMORY_CACHE = "dting:memory.%s";

    /**
     * cpu的缓存数据
     */
    private final static String DTING_CPU_CACHE = "dting:cpu:%s";

    /**
     * 实例信息
     */
    private final static String DTING_INSTANCE_CACHE = "dting:instance:data:%s:%s:%s";

    /**
     * 活跃的session格式化
     *
     * @param sessionId 会话id
     * @return 格式化号的数据
     */
    public static String sessionActiveKeyFormat(String sessionId) {
        return String.format(WEB_SOCKET_SESSION_ACTIVE_KEY, sessionId);
    }

    /**
     * 内存缓存数据
     *
     * @param sessionId 会话id
     * @return 格式化好的数据
     */
    public static String dtingMemoryCacheFormat(String sessionId) {
        return String.format(DTING_MEMORY_CACHE, sessionId);
    }

    /**
     * cpu缓存数据
     *
     * @param sessionId 会话id
     * @return 格式化好的数据
     */
    public static String dtingCpuCacheFormat(String sessionId) {
        return String.format(DTING_CPU_CACHE, sessionId);
    }

    /**
     * 实例缓存数据
     *
     * @param dtingEnvName      环境名称
     * @param dtingServerName   服务名称
     * @param dtingInstanceName 实例名称
     * @return 格式化好的数据
     */
    public static String dtingInstanceCacheFormat(String dtingEnvName, String dtingServerName, String dtingInstanceName) {
        return String.format(DTING_INSTANCE_CACHE, dtingEnvName, dtingServerName, dtingInstanceName);
    }

}
