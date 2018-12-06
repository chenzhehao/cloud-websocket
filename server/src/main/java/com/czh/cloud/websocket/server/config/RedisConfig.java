package com.czh.cloud.websocket.server.config;

import com.czh.cloud.websocket.server.service.RedisMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/12/4 13:22
 */

@Configuration("RedisConfig")
@EnableCaching
public class RedisConfig {
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);


    /**
     * Redis消息监听器容器
     *
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫pmp 的通道
        container.addMessageListener(listenerAdapter, new PatternTopic("redisChat"));
        //这个container 可以添加多个 messageListener
        return container;
    }

    /**
     * 配置消息接收处理类
     *
     * @param redisMsgService 自定义消息接收类
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RedisMsgService redisMsgService) {
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
        return new MessageListenerAdapter(redisMsgService, "receiveMessage");
    }

    /**redis 读取内容的template */
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}