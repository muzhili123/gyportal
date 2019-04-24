package com.gyportal.repository.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 未使用
 * create by lihuan at 18/11/8 17:14
 */
@Repository
public class NewsRedisRepository {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @SuppressWarnings("all")
    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valueOps;
}
