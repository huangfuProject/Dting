package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 描述线程池的配置信息<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/6 16:23
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("thread_pool_config")
public class ThreadPoolConfig extends DtingMessageBaseEntity implements Serializable {
    private static final long serialVersionUID = -4745535680404101109L;

    /**
     * 线程池的名称
     */
    private String threadPoolName;

    /**
     * 线程池的组名称 拼接规则为 server_env:server_key:instance_key:thread_pool_name
     */
    private String threadPoolGroupName;

    /**
     * 核心数
     */
    private Integer coreCount;

    /**
     * 最大线程数
     */
    private Integer maxCount;

    /**
     * 队列总长度
     */
    private Integer queueTotalSize;

    /**
     * 拒绝策略名称
     */
    private String rejectHandlerName;

    /**
     * 队列名称
     */
    private String queueTypeName;

    /**
     * 线程休眠时间
     */
    private Long keepAliveTime;
}
