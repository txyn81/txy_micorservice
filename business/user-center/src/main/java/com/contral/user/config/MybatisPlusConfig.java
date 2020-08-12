package com.contral.user.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.contral.core.config.DefaultMybatisPlusConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @authot tangxiyuan
 * @date 2020/8/1 9:34 下午
 * @description mybatis-plus配置类
 */
@Configuration
public class MybatisPlusConfig extends DefaultMybatisPlusConfig {

}
