package com.contral.common.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @description: TODO
 * @author: oren.tang
 * @date: 2020/8/15 2:58 下午
 */
@Data
@ConfigurationProperties(prefix = "txy.cache-manager")
public class CacheManagerProperties {
    private List<CacheConfig> configs;

    @Data
    public static class CacheConfig {

        /**
         * cache key
         */
        private String key;

        /**
         * 过期时间
         */
        private long second = 60;
    }
}
