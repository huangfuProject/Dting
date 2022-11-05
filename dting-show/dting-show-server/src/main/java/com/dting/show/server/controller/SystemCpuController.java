package com.dting.show.server.controller;

import com.dting.show.server.annotations.GlobalResultPackage;
import com.dting.show.server.conditions.MonitorBatchCondition;
import com.dting.show.server.service.MessageCpuSnapshotService;
import com.dting.show.server.vos.monitoring.SystemCpuDataVo;
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
@RequestMapping("/cpu")
public class SystemCpuController {

    private final MessageCpuSnapshotService messageCpuSnapshotService;

    public SystemCpuController(MessageCpuSnapshotService messageCpuSnapshotService) {
        this.messageCpuSnapshotService = messageCpuSnapshotService;
    }


    @PostMapping("cpuMonitoring")
    public SystemCpuDataVo memoryMonitoring(@RequestBody MonitorBatchCondition monitorBatchCondition) {
        return messageCpuSnapshotService.cpuMonitoring(monitorBatchCondition, true);
    }
}
