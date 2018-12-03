package com.czh.cloud.websocket.outer.service.impl;

import com.czh.cloud.websocket.outer.entity.request.SwaggerReq;
import com.czh.cloud.websocket.outer.entity.response.SwaggerRep;
import com.czh.cloud.websocket.outer.service.ISwaggerServiceClient;
import com.czh.cloud.common.entity.RootResponse;
import com.czh.cloud.common.entity.RootResultCode;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/8/10 10:57
 */
@Component
public class SwaggerServiceFailFactory implements FallbackFactory<ISwaggerServiceClient> {
    public static final Logger logger = LoggerFactory.getLogger(SwaggerServiceFailFactory.class);

    @Override
    public ISwaggerServiceClient create(Throwable cause) {
        RootResponse response = new RootResponse<String>(RootResultCode.SYSTEM_INNER_BUSY);

        return new ISwaggerServiceClient() {
            @Override
            public RootResponse<String> v1GetTest2() {
                logger.error("service:ISwaggerServiceClient interface:get 触发熔断机制", cause);
                return response;
            }

            @Override
            public RootResponse<SwaggerRep> v1PostTest1(String token, Integer type, SwaggerReq swaggerReq) {
                logger.error("service:ISwaggerServiceClient interface:v1PostTest1 触发熔断机制", cause);
                return response;
            }
        };
    }
}