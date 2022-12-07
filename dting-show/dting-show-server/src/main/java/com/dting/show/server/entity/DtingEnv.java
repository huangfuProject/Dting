package com.dting.show.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 环境信息<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:15
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DtingEnv extends BaseDting {
    private static final long serialVersionUID = 5631514893946972892L;


    /**
     * 环境名称
     */
    private String envName;

    /**
     * 服务id
     */
    private Integer serverId;

    /**
     * 创建时间
     */
    private Long createDate;

}
