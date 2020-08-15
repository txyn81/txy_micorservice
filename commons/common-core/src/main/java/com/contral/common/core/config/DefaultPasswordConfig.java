package com.contral.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description: 将密码工具类从各微服务中提取为公共服务
 * @author: oren.tang
 * @date: 2020/8/15 4:16 下午
 */
public class DefaultPasswordConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
