package com.czh.cloud.websocket.server.controller;

import com.czh.cloud.websocket.outer.entity.request.SwaggerReq;
import com.czh.cloud.websocket.outer.entity.response.SwaggerRep;
import com.czh.cloud.websocket.outer.util.SwaggerServiceUtil;
import com.czh.cloud.websocket.server.mapper.PDoctorMapper;
import com.czh.cloud.common.controller.RootController;
import com.czh.cloud.common.entity.RootResponse;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/5 11:16
 */
@RestController
@RequestMapping("/swagger")
@Api(value = "swagger", description = "swagger controller接口样例")
public class SwaggerController extends RootController {
    public static final Logger logger = LoggerFactory.getLogger(SwaggerController.class);

    @Autowired
    PDoctorMapper doctorMapper;

    @ApiOperation(value = "test0测试接口功能描述", response = String.class, notes = "此处描述的返回对象为data内容，外层有code和message")
    @RequestMapping(value = "/v1/test0", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RootResponse<SwaggerRep> v1GetTest0() {
        SwaggerReq swaggerReq = new SwaggerReq();
        swaggerReq.setReq1(11111);
        swaggerReq.setReq2("aaaaaa");
        return SwaggerServiceUtil.v1PostTest1("token11111", 22222, swaggerReq);
    }

    @ApiOperation(value = "test1测试接口功能描述", response = String.class, notes = "此处描述的返回对象为data内容，外层有code和message")
    @RequestMapping(value = "/v1/test1", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RootResponse<String> v1GetTest1() {
        return SwaggerServiceUtil.v1GetTest2();
    }

    @ApiOperation(value = "test2测试接口功能描述", response = String.class, notes = "此处描述的返回对象为data内容，外层有code和message")
    @RequestMapping(value = "/v1/test2", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RootResponse<String> v1GetTest2() {
        RootResponse<String> response = RootResponse.instance();
        response.setData(doctorMapper.selectByPrimaryKey(1384).getName());
        return response;
    }

    @ApiOperation(value = "post接口功能描述", response = SwaggerRep.class, notes = "此处描述的返回对象为data内容，外层有code和message")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "用户token值", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "type", value = "类型", required = true)})
    @RequestMapping(value = "/v1/test3/{type}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public RootResponse<SwaggerRep> v1PostTest1(@RequestHeader(value = "token") String token,
                                                @PathVariable Integer type,
                                                @Valid @RequestBody SwaggerReq swaggerReq,
                                                final BindingResult bindingResult) {
        logger.info("token=" + token + " type=" + type);
        SwaggerRep rep = new SwaggerRep();
        rep.setReq1(swaggerReq.getReq1());
        rep.setReq2(swaggerReq.getReq2());
        RootResponse<SwaggerRep> response = RootResponse.instance();
        response.setData(rep);
        return response;
    }

    @ApiOperation(value = "get接口功能描述", response = SwaggerRep.class, notes = "此处描述的返回对象为data内容，外层有code和message")
    @RequestMapping(value = "/v1/swagger/{type}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RootResponse<SwaggerRep> v1Get(@ApiParam(name = "token", value = "用户token值", example = "123", required = true) @RequestHeader(value = "token") String token,
                                          @ApiParam(name = "type", value = "类型 ", example = "1", required = true) @PathVariable Integer type,
                                          @ApiParam(name = "resultCheckStatus", value = "请求值", example = "123ds", required = true) @RequestParam String resultCheckStatus,
                                          @ApiParam(name = "swaggerReq", value = "样例对象", required = true) @RequestBody SwaggerReq swaggerReq) {
        return null;
    }

    @ApiOperation(value = "post接口功能描述", response = SwaggerRep.class, notes = "此处描述的返回对象为data内容，外层有code和message")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "用户token值", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "type", value = "类型", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "resultCheckStatus", value = "请求值", required = true)})
    @RequestMapping(value = "/v1/swagger/{type}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public RootResponse<SwaggerRep> v1Post(@RequestHeader(value = "token") String token,
                                           @PathVariable Integer type,
                                           @RequestParam String resultCheckStatus,
                                           @RequestBody SwaggerReq swaggerReq) {
        return null;
    }
}
