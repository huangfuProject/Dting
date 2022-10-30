package com.dting.show.server.controller;

import com.dting.show.server.conditions.MemoryBatchCondition;
import com.dting.show.server.service.MessageMemorySnapshotService;
import com.dting.show.server.vos.monitoring.MemoryDataMonitoringVo;
import com.dting.show.server.vos.monitoring.MemoryDataVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统内存前端控制器
 *
 * @author huangfu
 * @date 2022年10月28日15:23:30
 */
@RestController
@RequestMapping("/memory")
public class SystemMemoryController {

    private final MessageMemorySnapshotService messageMemorySnapshotService;

    public SystemMemoryController(MessageMemorySnapshotService messageMemorySnapshotService) {
        this.messageMemorySnapshotService = messageMemorySnapshotService;
    }

    @PostMapping("memoryMonitoring")
    public MemoryDataMonitoringVo memoryMonitoring(@RequestBody MemoryBatchCondition memoryBatchCondition){
        return messageMemorySnapshotService.memoryMonitoring(memoryBatchCondition, true);
    }

    /**
     * 批量的根据条件获取内存数据
     *
     * @param memoryBatchCondition 内存批处理条件
     * @return 返回所有的内存数据
     */
    @PostMapping("findMemoryData")
    public MemoryDataVo findMemoryData(@RequestBody MemoryBatchCondition memoryBatchCondition) {
        return messageMemorySnapshotService.memoryQueryByCondition(memoryBatchCondition);
    }
}
