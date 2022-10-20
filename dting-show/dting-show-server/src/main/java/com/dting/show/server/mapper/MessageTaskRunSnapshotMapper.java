package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.MessageTaskRunSnapshot;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 线程池任务执行日志
 *
 * @author huangfu
 * @date 2022年10月20日11:33:16
 */
@Repository
public interface MessageTaskRunSnapshotMapper extends BaseMapper<MessageTaskRunSnapshot> {
    /**
     * 忽略唯一性检查的 批量插入
     *
     * @param messageTaskRunSnapshotList 要插入的数据
     */
    void ignoreOnlyBatchInsert(@Param("messageTaskRunLogDataList") List<MessageTaskRunSnapshot> messageTaskRunSnapshotList);


}
