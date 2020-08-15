package com.contral.common.redis.constant;

/**
 * @description: redis 工具常量
 * @author: oren.tang
 * @date: 2020/8/15 3:54 下午
 */
public class RedisToolsConstant {
    private RedisToolsConstant() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * single Redis
     */
    public final static int SINGLE = 1 ;

    /**
     * Redis cluster
     */
    public final static int CLUSTER = 2 ;
}
