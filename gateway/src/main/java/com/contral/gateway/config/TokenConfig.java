package com.contral.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @authot tangxiyuan
 * @date 2020/8/2 11:02 下午
 * @description 令牌服务配置，使用JWT存储令牌
 */
@Configuration
public class TokenConfig {

    private final String SIGNING_KEY = "uaa";

    /**
     * @author tangxiyuan
     * @date 2020/8/6 12:19 上午
     * @return
     * @description 将原本使用内存的存储方式，换为JWT令牌的存储方式
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * @author tangxiyuan
     * @date 2020/8/6 12:22 上午
     * @return
     * @description 令牌要经过一系列加密算法，所以要配置JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对称密钥，资源服务器使用该密钥来验证
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
