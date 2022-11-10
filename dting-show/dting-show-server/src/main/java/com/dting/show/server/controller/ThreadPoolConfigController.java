package com.dting.show.server.controller;

import com.dting.show.server.annotations.GlobalResultPackage;
import com.dting.show.server.dto.ThreadPoolConfigDto;
import com.dting.show.server.service.ThreadPoolConfigService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 线程池配置的控制器
 *
 * @author huangfu
 * @date 2022/11/10 8:35
 */
@RestController
@GlobalResultPackage
@RequestMapping("threadPoolConfig")
public class ThreadPoolConfigController {

    private final ThreadPoolConfigService configService;

    public ThreadPoolConfigController(ThreadPoolConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("update")
    public void update(@RequestBody ThreadPoolConfigDto threadPoolConfigDto) {
        this.configService.updateById(threadPoolConfigDto);
    }
}
