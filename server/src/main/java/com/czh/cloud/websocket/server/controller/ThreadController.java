package com.czh.cloud.websocket.server.controller;

import com.czh.cloud.websocket.server.service.IThreadService;
import com.czh.cloud.common.controller.RootController;
import com.czh.cloud.common.entity.RootResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/11 15:46
 */
@RestController
@RequestMapping("/thread")
@Api(value = "thread", description = "thread controller接口样例")
public class ThreadController extends RootController {
    public static final Logger logger = LoggerFactory.getLogger(SwaggerController.class);

    @Autowired
    IThreadService threadService;

    @ApiOperation(value = "test0异步线程测试", response = String.class, notes = "此处描述的返回对象为data内容，外层有code和message")
    @RequestMapping(value = "/v1/test0", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RootResponse<String> v1GetTest0(@ApiParam(name = "param0", value = "请求值0", example = "abc", required = true) @RequestParam String param0,
                                           @ApiParam(name = "param1", value = "请求值1", example = "abc", required = false) @RequestParam(required = false) String param1) {
        logger.info("param0=" + param0 + " param1=" + param1);
        RootResponse<String> response = RootResponse.instance();
        response.setData(threadService.threadTest0());
        return response;
    }

    @ApiOperation(value = "test1异步线程测试(带返回值)", response = String.class, notes = "此处描述的返回对象为data内容，外层有code和message")
    @RequestMapping(value = "/v1/test1", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RootResponse<String> v1GetTest1(@ApiParam(name = "param0", value = "请求值0", example = "15", required = true) @RequestParam int param0,
                                           @ApiParam(name = "param1", value = "请求值1", example = "abc", required = false) @RequestParam(required = false) String param1) throws InterruptedException, ExecutionException {
        logger.info("param0=" + param0 + " param1=" + param1);
        RootResponse<String> response = RootResponse.instance();

//        Future<String> future = threadService.threadTest1();
//        while (!future.isDone()) {
//            Thread.sleep(200);
//        }
//        response.setData(future.get());

        for (int i = 0; i < param0; i++) {
            threadService.threadTest1();
        }
        return response;
    }

    @ApiOperation(value = "test2 HystrixCommand测试服务调用", response = String.class, notes = "此处描述的返回对象为data内容，外层有code和message")
    @RequestMapping(value = "/v1/test2", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RootResponse<String> v1GetTest2() {
        return threadService.threadTest2();
    }

    @ApiOperation(value = "test3 HystrixCommand测试服务提供", response = String.class, notes = "此处描述的返回对象为data内容，外层有code和message")
    @RequestMapping(value = "/v1/test3", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RootResponse<String> v1GetTest3() {
        RootResponse<String> response = RootResponse.instance();
        response.setData(threadService.threadTest3());
        return response;
    }
}
