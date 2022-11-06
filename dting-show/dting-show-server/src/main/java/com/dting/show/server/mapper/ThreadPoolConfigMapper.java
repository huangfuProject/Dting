package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.ThreadPoolConfig;
import org.springframework.stereotype.Repository;

/**
 * *************************************************<br/>
 * 线程池配置信息ORM<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/6 16:31
 */
@Repository
public interface ThreadPoolConfigMapper extends BaseMapper<ThreadPoolConfig> {
}
