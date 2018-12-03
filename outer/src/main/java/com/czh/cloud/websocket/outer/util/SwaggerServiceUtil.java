package com.czh.cloud.websocket.outer.util;

import com.czh.cloud.websocket.outer.entity.request.SwaggerReq;
import com.czh.cloud.websocket.outer.entity.response.SwaggerRep;
import com.czh.cloud.websocket.outer.service.ISwaggerServiceClient;
import com.czh.cloud.websocket.outer.service.impl.SwaggerServiceClient;
import com.czh.cloud.common.entity.RootException;
import com.czh.cloud.common.entity.RootResponse;
import com.czh.cloud.common.entity.RootResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/8/17 14:31
 */
@Component
public class SwaggerServiceUtil {
    public static final Logger logger = LoggerFactory.getLogger(SwaggerServiceUtil.class);

    private static ISwaggerServiceClient swaggerServiceClient;
    private static SwaggerServiceClient swaggerServiceClient2;

    @Autowired
    public SwaggerServiceUtil(ISwaggerServiceClient swaggerServiceClient, SwaggerServiceClient swaggerServiceClient2) {
        SwaggerServiceUtil.swaggerServiceClient = swaggerServiceClient;
        SwaggerServiceUtil.swaggerServiceClient2 = swaggerServiceClient2;
    }

    /**
     * 功能描述: 普通模式封装调用
     *
     * @author: zhehao.chen
     * @version: V1.0
     * @date: 2018/9/11 14:16
     * @param: []
     * @return: com.czh.cloud.common.entity.RootResponse<java.lang.String>
     */
    public static RootResponse<String> v1GetTest2() {
        RootResponse<String> response;
        try {
            logger.info("调用swagger v1GetTest2访问外部系统get params：");
            response = swaggerServiceClient.v1GetTest2();
            logger.info("调用swagger v1GetTest2访问外部系统get response：" + response);
        } catch (Exception e) {
            throw new RootException(RootResultCode.SYSTEM_INNER_BUSY.code(), e.getMessage());
        }
        if (!RootResultCode.SUCCESS.code().equals(response.getCode())) {
            throw new RootException(response.getCode(), response.getMessage());
        }
        return response;
    }

    /**
     * 功能描述: 命令模式封装调用
     *
     * @author: zhehao.chen
     * @version: V1.0
     * @date: 2018/9/11 14:16
     * @param: [token, type, swaggerReq]
     * @return: com.czh.cloud.common.entity.RootResponse<com.czh.cloud.websocket.outer.entity.response.SwaggerRep>
     */
    public static RootResponse<SwaggerRep> v1PostTest1(String token, Integer type, SwaggerReq swaggerReq) {
        RootResponse<SwaggerRep> response;
        try {
            logger.info("调用swagger v1PostTest1访问外部系统get params：");
            response = swaggerServiceClient2.v1PostTest1(token, type, swaggerReq);
            logger.info("调用swagger v1PostTest1访问外部系统get response：" + response);
        } catch (Exception e) {
            throw new RootException(RootResultCode.SYSTEM_INNER_BUSY.code(), e.getMessage());
        }
        if (!RootResultCode.SUCCESS.code().equals(response.getCode())) {
            throw new RootException(response.getCode(), response.getMessage());
        }
        return response;
    }
}
