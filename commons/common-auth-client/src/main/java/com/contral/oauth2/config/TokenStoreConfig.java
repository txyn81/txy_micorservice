package com.contral.oauth2.config;

import com.contral.oauth2.store.AuthDbTokenStore;
import com.contral.oauth2.store.AuthJwtTokenStore;
import com.contral.oauth2.store.AuthRedisTokenStore;
import com.contral.oauth2.store.ResJwtTokenStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description: token存储配置
 * @author: oren.tang
 * @date: 2020/8/15 2:46 下午
 */
@EnableCaching
public class TokenStoreConfig {

    @Configuration
    @ConditionalOnProperty(prefix = "txy.oauth2.token.store", name = "type", havingValue = "db")
    @Import(AuthDbTokenStore.class)
    public class JdbcTokenConfig {

    }

    @Configuration
    @ConditionalOnProperty(prefix = "txy.oauth2.token.store", name = "type", havingValue = "redis", matchIfMissing = true)
    @Import(AuthRedisTokenStore.class)
    public class RedisTokenConfig {

    }


    @Configuration
    @ConditionalOnProperty(prefix = "txy.oauth2.token.store", name = "type", havingValue = "authJwt")
    @Import(AuthJwtTokenStore.class)
    public class AuthJwtTokenConfig {

    }


    @Configuration
    @ConditionalOnProperty(prefix = "txy.oauth2.token.store", name = "type", havingValue = "resJwt")
    @Import(ResJwtTokenStore.class)
    public class ResJwtTokenConfig {

    }
}
