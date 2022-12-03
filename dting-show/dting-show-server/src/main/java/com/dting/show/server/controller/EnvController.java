package com.dting.show.server.controller;

import com.dting.show.server.annotations.GlobalResultPackage;
import com.dting.show.server.conditions.EnvCondition;
import com.dting.show.server.entity.DtingEnv;
import com.dting.show.server.service.DtingEnvService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * *************************************************<br/>
 * 环境相关的controller<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/3 13:25
 */
@RestController
@GlobalResultPackage
@RequestMapping("/env")
public class EnvController {

    private final DtingEnvService dtingEnvService;

    public EnvController(DtingEnvService dtingEnvService) {
        this.dtingEnvService = dtingEnvService;
    }

    /**
     * 根据条件查询所有的环境列表
     * @param envCondition 环境条件
     * @return 返回环境列表
     */
    @PostMapping("findAllDtingEnv")
    public List<DtingEnv> findAllDtingEnv(@RequestBody EnvCondition envCondition){
        return dtingEnvService.findByEnvCondition(envCondition);
    }
}
