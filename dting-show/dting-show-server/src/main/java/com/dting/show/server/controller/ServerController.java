package com.dting.show.server.controller;

import com.dting.show.server.annotations.GlobalResultPackage;
import com.dting.show.server.conditions.ServerCondition;
import com.dting.show.server.entity.DtingServer;
import com.dting.show.server.service.DtingServerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * *************************************************<br/>
 * 服务相关的处理器<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/3 13:57
 */
@RestController
@GlobalResultPackage
@RequestMapping("/server")
public class ServerController {

    private final DtingServerService dtingServerService;

    public ServerController(DtingServerService dtingServerService) {
        this.dtingServerService = dtingServerService;

    }

    /**
     * 查询服务列表
     *
     * @param serverCondition 服务条件
     * @return 服务列表
     */
    @PostMapping("findServerList")
    public List<DtingServer> findServerList(@RequestBody ServerCondition serverCondition) {
        return dtingServerService.findByServerCondition(serverCondition);
    }
}
