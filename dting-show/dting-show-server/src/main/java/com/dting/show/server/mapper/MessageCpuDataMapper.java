package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.MessageCpuData;
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
public interface MessageCpuDataMapper extends BaseMapper<MessageCpuData> {
    /**
     * 忽略唯一性检查的 批量插入
     *
     * @param messageCpuDataList 要插入的数据
     */
    void ignoreOnlyBatchInsert(@Param("messageCpuDataList") List<MessageCpuData> messageCpuDataList);
}
