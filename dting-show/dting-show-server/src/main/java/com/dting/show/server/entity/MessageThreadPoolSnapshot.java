package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 线程池的定时采样快照数据
 *
 * @author huangfu
 * @date 2022年10月20日16:17:06
 */
@Data
@TableName("message_thread_pool_snapshot")
@EqualsAndHashCode(callSuper = true)
public class MessageThreadPoolSnapshot extends DtingMessageBaseEntity implements Serializable {
    private static final long serialVersionUID = 62511851809033177L;

    /**
     * 关联主键
     */
    private String collectKey;
}
