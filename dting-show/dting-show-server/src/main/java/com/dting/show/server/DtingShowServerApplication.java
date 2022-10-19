package com.dting.show.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 消息展示服务启动器
 *
 * @author huangfu
 * @date 2022年10月18日11:48:03
 */
@SpringBootApplication
@MapperScan("com.dting.show.server.mapper")
public class DtingShowServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtingShowServerApplication.class, args);
    }
}
