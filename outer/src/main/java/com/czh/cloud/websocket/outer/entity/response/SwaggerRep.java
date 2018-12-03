package com.czh.cloud.websocket.outer.entity.response;

import com.czh.cloud.common.entity.RootRep;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/5 11:25
 */
@ApiModel(value = "SwaggerRep对象", description = "样例对象")
public class SwaggerRep extends RootRep {

    @ApiModelProperty(name = "rep1", value = "返回参数1", example = "123")
    public Integer req1;
    @ApiModelProperty(name = "rep2", value = "返回参数2", example = "abc")
    public String req2;

    public Integer getReq1() {
        return req1;
    }

    public void setReq1(Integer req1) {
        this.req1 = req1;
    }

    public String getReq2() {
        return req2;
    }

    public void setReq2(String req2) {
        this.req2 = req2;
    }
}
