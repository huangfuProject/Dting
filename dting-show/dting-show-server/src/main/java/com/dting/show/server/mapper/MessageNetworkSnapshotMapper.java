package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.MessageNetworkSnapshot;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 网卡数据
 *
 * @author huangfu
 * @date 2022年10月20日11:08:36
 */
@Repository
public interface MessageNetworkSnapshotMapper extends BaseMapper<MessageNetworkSnapshot> {

    /**
     * 批量插入
     *
     * @param messageNetworkSnapshotList 要插入的数据
     */
    void batchInsert(@Param("messageNetworkSnapshotList") List<MessageNetworkSnapshot> messageNetworkSnapshotList);

}
