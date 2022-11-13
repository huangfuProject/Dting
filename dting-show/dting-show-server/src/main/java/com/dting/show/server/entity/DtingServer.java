package com.dting.show.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 服务信息<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 8:19
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DtingServer extends BaseDting implements Serializable {
    private static final long serialVersionUID = -4206889777337425189L;

    /**
     * 服务名称
     */
    private String serverName;

    /**
     * 环境id
     */
    private Integer envId;

    /**
     * 创建时间
     */
    private Long createDate;
}
