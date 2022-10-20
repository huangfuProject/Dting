package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.ThreadPoolDetailedSnapshot;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 线程池的采集信息ORM
 *
 * @author huangfu
 * @date 2022年10月20日15:16:40
 */
@Repository
public interface ThreadPoolDetailedSnapshotMapper extends BaseMapper<ThreadPoolDetailedSnapshot> {

    /**
     * 批量插入
     * @param threadPoolDetailedSnapshots  网卡子表数据
     */
    void batchInsert(@Param("threadPoolDetailedSnapshots") List<ThreadPoolDetailedSnapshot> threadPoolDetailedSnapshots);
}
