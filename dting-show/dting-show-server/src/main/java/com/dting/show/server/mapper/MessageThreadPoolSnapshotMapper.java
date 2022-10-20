package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.MessageThreadPoolSnapshot;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 线程池快照统计ORM
 *
 * @author huangfu
 * @date 2022年10月20日16:19:15
 */
@Repository
public interface MessageThreadPoolSnapshotMapper extends BaseMapper<MessageThreadPoolSnapshot> {
    /**
     * 忽略唯一性检查的 批量插入
     *
     * @param messageThreadPoolSnapshotList 要插入的数据
     */
    void ignoreOnlyBatchInsert(@Param("messageThreadPoolSnapshotList") List<MessageThreadPoolSnapshot> messageThreadPoolSnapshotList);

}
