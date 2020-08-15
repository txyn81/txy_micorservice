package com.contral.common.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @authot tangxiyuan
 * @date 2020/8/1 9:34 下午
 * @description mybatis-plus配置类
 */
@EnableTransactionManagement
@Configuration
public class DefaultMybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        //分页拦截
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}
