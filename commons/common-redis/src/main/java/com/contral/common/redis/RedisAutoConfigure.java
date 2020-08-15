package com.contral.common.redis;

import com.contral.common.redis.properties.CacheManagerProperties;
import com.contral.common.redis.template.RedisRepository;
import com.contral.common.redis.util.RedisObjectSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.collections.RedisProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: redis 配置类
 * @author: oren.tang
 * @date: 2020/8/15 2:55 下午
 */
@EnableCaching
@EnableConfigurationProperties({RedisProperties.class, CacheManagerProperties.class})
public class RedisAutoConfigure {
    @Autowired
    private CacheManagerProperties cacheManagerProperties;

    /**
     * @description: RedisTemplate配置
     *
     * @authot: oren.tang
     * @date: 2020/8/15 3:06 下午
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        RedisSerializer stringSerializer = new StringRedisSerializer();
        RedisSerializer redisObjectSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(redisObjectSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Redis Repository
     * @param redisTemplate
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisRepository redisRepository(RedisTemplate<String, Object> redisTemplate) {
        return new RedisRepository(redisTemplate);
    }

    @Bean(name = "cacheManager")
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration difConf = getDefConf().entryTtl(Duration.ofHours(1));

        //自定义的缓存过期时间配置
        int configSize = cacheManagerProperties.getConfigs() == null ? 0 : cacheManagerProperties.getConfigs().size();
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(configSize);
        if (configSize > 0) {
            cacheManagerProperties.getConfigs().forEach(e -> {
                RedisCacheConfiguration conf = getDefConf().entryTtl(Duration.ofSeconds(e.getSecond()));
                redisCacheConfigurationMap.put(e.getKey(), conf);
            });
        }

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(difConf)
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":" + method.getName() + ":");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    private RedisCacheConfiguration getDefConf() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .computePrefixWith(cacheName -> "cache".concat(":").concat(cacheName).concat(":"))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new RedisObjectSerializer()));
    }
}
