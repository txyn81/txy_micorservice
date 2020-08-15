package com.contral.common.db.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @description: Mybatis-plus配置
 * @author: oren.tang
 * @date: 2020/8/15 4:30 下午
 */
@Import(DateMetaObjectHandler.class)
public class DefaultMyBatisPlusConfig {

    /**
     * 分页插件，自动识别数据库插件
     * 如要配置多租户及自动语言配置，参考项目中的配置（前公司）
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
