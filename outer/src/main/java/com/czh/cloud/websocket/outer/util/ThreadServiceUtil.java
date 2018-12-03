package com.czh.cloud.websocket.outer.util;

import com.czh.cloud.websocket.outer.service.impl.ThreadServiceClient;
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
 * @date: 2018/9/11 17:36
 */
@Component
public class ThreadServiceUtil {

    public static final Logger logger = LoggerFactory.getLogger(ThreadServiceUtil.class);
    private static ThreadServiceClient threadServiceClient;

    @Autowired
    public ThreadServiceUtil(ThreadServiceClient threadServiceClient) {
        ThreadServiceUtil.threadServiceClient = threadServiceClient;
    }

    public static RootResponse<String> v1GetTest3() {
        RootResponse<String> response;
        try {
            logger.info("调用ThreadServiceUtil v1GetTest3访问外部系统get params：");
            response = threadServiceClient.v1GetTest3();
            logger.info("调用ThreadServiceUtil v1GetTest3访问外部系统get response：" + response);
        } catch (Exception e) {
            throw new RootException(RootResultCode.SYSTEM_INNER_BUSY.code(), e.getMessage());
        }
        if (!RootResultCode.SUCCESS.code().equals(response.getCode())) {
            throw new RootException(response.getCode(), response.getMessage());
        }
        return response;
    }
}
