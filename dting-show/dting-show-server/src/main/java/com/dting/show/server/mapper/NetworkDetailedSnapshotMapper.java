package com.dting.show.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dting.show.server.entity.NetworkDetailedSnapshot;
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
public interface NetworkDetailedSnapshotMapper extends BaseMapper<NetworkDetailedSnapshot> {

    /**
     * 批量插入
     * @param networkDetailedSnapshotList 网卡子表数据
     */
    void batchInsert(@Param("networkDetailedSnapshotList") List<NetworkDetailedSnapshot> networkDetailedSnapshotList);
}
