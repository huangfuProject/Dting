package com.dting.show.server.dto;

import com.dting.show.server.entity.DtingEnv;
import com.dting.show.server.entity.DtingInstance;
import com.dting.show.server.entity.DtingServer;
import lombok.Data;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 实例的数据<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 9:15
 */
@Data
public class InstanceData implements Serializable {
    private static final long serialVersionUID = -5214593914127824408L;

    /**
     * 环境信息
     */
    private DtingEnv dtingEnv;
    /**
     * 服务信息
     */
    private DtingServer dtingServer;
    /**
     * 实例信息
     */
    private DtingInstance dtingInstance;


}
