package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.ThreadPoolInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 线程池的采集信息ORM
 *
 * @author huangfu
 * @date 2022年10月20日15:16:40
 */
public interface ThreadPoolInfoMapper extends BaseMapper<ThreadPoolInfo> {

    /**
     * 批量插入
     * @param threadPoolInfos  网卡子表数据
     */
    void batchInsert(@Param("threadPoolInfos") List<ThreadPoolInfo> threadPoolInfos);
}
