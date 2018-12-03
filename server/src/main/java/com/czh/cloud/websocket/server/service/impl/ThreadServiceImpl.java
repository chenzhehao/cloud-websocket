package com.czh.cloud.websocket.server.service.impl;

import com.czh.cloud.websocket.outer.util.ThreadServiceUtil;
import com.czh.cloud.websocket.server.controller.SwaggerController;
import com.czh.cloud.websocket.server.service.IThreadService;
import com.czh.cloud.common.entity.RootResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/11 15:49
 */
@Service
public class ThreadServiceImpl implements IThreadService {
    public static final Logger logger = LoggerFactory.getLogger(SwaggerController.class);

    @Override
    @Async
    public String threadTest0() {
        logger.info("异步线程测试");
        return "异步线程测试";
    }

    @Override
    @Async
    public Future<String> threadTest1() throws InterruptedException {
        logger.info("异步线程测试(带返回值)");
        Thread.sleep(3000);
        return new AsyncResult<String>("异步线程测试(带返回值)");
    }

    @Override
    public RootResponse<String> threadTest2() {
        return ThreadServiceUtil.v1GetTest3();
    }

    @Override
    public String threadTest3() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "123";
    }
}
