package com.dting.show.server.controller;

import com.dting.show.server.annotations.GlobalResultPackage;
import com.dting.show.server.conditions.MonitorBatchCondition;
import com.dting.show.server.service.MessageMemorySnapshotService;
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
@GlobalResultPackage
@RequestMapping("/memory")
public class SystemMemoryController {

    private final MessageMemorySnapshotService messageMemorySnapshotService;

    public SystemMemoryController(MessageMemorySnapshotService messageMemorySnapshotService) {
        this.messageMemorySnapshotService = messageMemorySnapshotService;
    }

    @PostMapping("memoryMonitoring")
    public MemoryDataVo memoryMonitoring(@RequestBody MonitorBatchCondition monitorBatchCondition) {
        return messageMemorySnapshotService.memoryMonitoring(monitorBatchCondition, true);
    }
}
