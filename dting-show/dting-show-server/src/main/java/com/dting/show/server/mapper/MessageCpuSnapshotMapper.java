package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.MessageCpuSnapshot;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CpuORM
 *
 * @author huangfu
 * @date 2022年10月19日14:21:53
 */
@Repository
public interface MessageCpuSnapshotMapper extends BaseMapper<MessageCpuSnapshot> {
    /**
     * 批量插入
     *
     * @param messageCpuSnapshotList 要插入的数据
     */
    void batchInsert(@Param("messageCpuSnapshotList") List<MessageCpuSnapshot> messageCpuSnapshotList);
}
