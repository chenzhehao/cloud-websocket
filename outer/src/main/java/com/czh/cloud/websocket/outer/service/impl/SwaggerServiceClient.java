package com.czh.cloud.websocket.outer.service.impl;

import com.czh.cloud.websocket.outer.entity.request.SwaggerReq;
import com.czh.cloud.websocket.outer.entity.response.SwaggerRep;
import com.czh.cloud.websocket.outer.service.ISwaggerServiceClient;
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
 * @date: 2018/9/10 17:45
 */
@Service
public class SwaggerServiceClient {
    public static final Logger logger = LoggerFactory.getLogger(SwaggerServiceFailFactory.class);

    @Autowired
    private ISwaggerServiceClient swaggerServiceClient;

    @HystrixCommand(fallbackMethod = "myFallback", threadPoolKey = "queryThreadPoolKey", groupKey = "queryGroupKey", commandKey = "queryCommandKey")
    public RootResponse<SwaggerRep> v1PostTest1(String token, Integer type, SwaggerReq swaggerReq) {
        logger.info("HystrixCommand独立线程");
        return swaggerServiceClient.v1PostTest1(token, type, swaggerReq);
    }

    /**
     * 方法simpleHystrixClientCall的回退方法，可以指定将hystrix执行失败异常传入到方法中
     *
     * @param token，type，swaggerReq ystrix执行失败的传入方法的请求
     * @param cause                 hystrix执行失败的异常对象
     * @return
     */
    RootResponse<SwaggerRep> myFallback(String token, Integer type, SwaggerReq swaggerReq, Throwable cause) {
        RootResponse response = new RootResponse<SwaggerRep>(RootResultCode.SYSTEM_INNER_BUSY);
        logger.error("service:SwaggerServiceClient interface:v1PostTest1 触发熔断机制{token=" + token + " type=" + type + " swaggerReq=" + swaggerReq + "}", cause);
        return response;
    }

}
