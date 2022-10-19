package com.dting.show.server.factorys;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis的分页插件
 *
 * @author huangfu
 * @date 2022年10月19日14:13:30
 */
@Configuration
public class MybatisPluginFactory {

    /**
     * 分页插件
     *
     * @return 分页插件
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }
}
