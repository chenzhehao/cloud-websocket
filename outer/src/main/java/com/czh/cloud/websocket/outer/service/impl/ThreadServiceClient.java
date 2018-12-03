package com.czh.cloud.websocket.outer.service.impl;

import com.czh.cloud.websocket.outer.service.IThreadServiceClient;
import com.czh.cloud.common.entity.RootResponse;
import com.czh.cloud.common.entity.RootResultCode;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/11 17:27
 */
@Service
public class ThreadServiceClient {
    public static final Logger logger = LoggerFactory.getLogger(ThreadServiceClient.class);

    @Autowired
    IThreadServiceClient threadServiceClient;

    @HystrixCommand(fallbackMethod = "myFallback", threadPoolKey = "queryThreadPoolKey", groupKey = "queryGroupKey", commandKey = "queryCommandKey")
    public RootResponse<String> v1GetTest3() {
        logger.info("HystrixCommand独立线程");
        return threadServiceClient.v1GetTest3();
    }

    /**
     * 方法simpleHystrixClientCall的回退方法，可以指定将hystrix执行失败异常传入到方法中
     *
     * @param cause hystrix执行失败的异常对象
     * @return
     */
    RootResponse<String> myFallback(Throwable cause) {
        RootResponse response = new RootResponse<String>(RootResultCode.SYSTEM_INNER_BUSY);
        logger.error("service:ThreadServiceClient interface:v1GetTest3 触发熔断机制", cause);
        return response;
    }

}
