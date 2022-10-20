package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.MessageNetworkChildData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 网卡具体的详细的信息ORM
 *
 * @author huangfu
 * @date 2022年10月20日13:50:48
 */
@Repository
public interface MessageNetworkChildDataMapper extends BaseMapper<MessageNetworkChildData> {

    /**
     * 批量插入
     * @param messageNetworkChildDataList 网卡子表数据
     */
    void batchInsert(@Param("messageNetworkChildDataList") List<MessageNetworkChildData> messageNetworkChildDataList);
}
