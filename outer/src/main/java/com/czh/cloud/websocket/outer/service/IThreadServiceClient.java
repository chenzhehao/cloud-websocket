package com.czh.cloud.websocket.outer.service;

import com.czh.cloud.common.entity.RootResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/11 17:17
 */
@FeignClient(value = "czh-archetype2")//命令模式使用
@RequestMapping(value = "/archetype2/thread", headers = {"sysCode=33333", "sysCode2=44444"}, produces = "application/json;charset=UTF-8")
public interface IThreadServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/test3")
    RootResponse<String> v1GetTest3();

}
