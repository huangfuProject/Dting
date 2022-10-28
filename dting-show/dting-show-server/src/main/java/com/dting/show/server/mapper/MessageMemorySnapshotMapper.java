package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.MessageMemorySnapshot;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 内存使用情况的ORM
 *
 * @author huangfu
 * @date 2022年10月20日08:14:12
 */
@Repository
public interface MessageMemorySnapshotMapper extends BaseMapper<MessageMemorySnapshot> {

    /**
     * 批量插入
     *
     * @param messageMemorySnapshotList 要插入的数据
     */
    void batchInsert(@Param("messageMemorySnapshotList") List<MessageMemorySnapshot> messageMemorySnapshotList);
}
