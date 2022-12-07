package com.dting.show.server.controller;

import com.dting.show.server.annotations.GlobalResultPackage;
import com.dting.show.server.conditions.InstanceCondition;
import com.dting.show.server.entity.DtingInstance;
import com.dting.show.server.service.DtingInstanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * *************************************************<br/>
 * 实例相关的服务<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/7 16:46
 */
@RestController
@GlobalResultPackage
@RequestMapping("/instance")
public class InstanceController {

    private final DtingInstanceService dtingInstanceService;

    public InstanceController(DtingInstanceService dtingInstanceService) {
        this.dtingInstanceService = dtingInstanceService;

    }

    /**
     * 查询服务列表
     *
     * @param instanceCondition 实例条件
     * @return 实例列表
     */
    @PostMapping("findInstanceList")
    public List<DtingInstance> findInstanceList(@RequestBody InstanceCondition instanceCondition) {
        return dtingInstanceService.findByInstanceCondition(instanceCondition);
    }
}
