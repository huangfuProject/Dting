package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.MessageMemoryData;
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
public interface MessageMemoryDataMapper extends BaseMapper<MessageMemoryData> {

    /**
     * 忽略唯一性检查的 批量插入
     *
     * @param messageMemoryDataList 要插入的数据
     */
    void ignoreOnlyBatchInsert(@Param("messageMemoryDataList") List<MessageMemoryData> messageMemoryDataList);
}
