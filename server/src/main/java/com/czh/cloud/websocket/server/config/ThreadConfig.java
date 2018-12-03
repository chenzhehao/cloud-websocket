package com.czh.cloud.websocket.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description: 系统线程池配置
 * @date: 2018/9/11 15:36
 */

@Configuration
@EnableAsync
public class ThreadConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(20);
        //设置最大线程数
        executor.setMaxPoolSize(500);
        //设置队列容量
        executor.setQueueCapacity(1000);
        //设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        //设置默认线程名称
        executor.setThreadNamePrefix("self-taskExecutor-");
        //允许核心线程超时
        executor.setAllowCoreThreadTimeOut(false);
        //设置拒绝策略
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

}
