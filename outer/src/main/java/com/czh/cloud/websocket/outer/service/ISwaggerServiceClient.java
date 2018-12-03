package com.czh.cloud.websocket.outer.service;

import com.czh.cloud.websocket.outer.entity.request.SwaggerReq;
import com.czh.cloud.websocket.outer.entity.response.SwaggerRep;
import com.czh.cloud.common.entity.RootResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/8/9 13:15
 */
//@FeignClient(value = "czh-archetype2", fallbackFactory = SwaggerServiceFailFactory.class)//正常模式使用
@FeignClient(value = "czh-archetype2")//命令模式使用
@RequestMapping(value = "/archetype2/swagger", headers = {"sysCode=33333", "sysCode2=44444"}, produces = "application/json;charset=UTF-8")
public interface ISwaggerServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/test2")
    RootResponse<String> v1GetTest2();

    @RequestMapping(value = "/v1/test3/{type}", headers = "token1=token22222", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    RootResponse<SwaggerRep> v1PostTest1(@RequestHeader(value = "token") String token,
                                         @PathVariable(value = "type") Integer type,
                                         @RequestBody SwaggerReq swaggerReq);
}