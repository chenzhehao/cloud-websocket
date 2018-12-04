package com.czh.cloud.websocket.server.service;

import com.pica.cloud.foundation.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/12/4 11:43
 */
@Component
public class RedisService {

    public static RedisClient redisClient;
    public static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisService(RedisClient redisClient, StringRedisTemplate stringRedisTemplate) {
        this.redisClient = redisClient;
        this.stringRedisTemplate = stringRedisTemplate;
    }

}
