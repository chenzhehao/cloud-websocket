package com.czh.cloud.websocket.outer.entity.request;

import com.czh.cloud.common.entity.RootReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/5 11:24
 */
@ApiModel(value = "SwaggerReq对象", description = "样例对象")
public class SwaggerReq extends RootReq {

    @ApiModelProperty(name = "req1", value = "请求参数1", example = "123")
    public Integer req1;

    @ApiModelProperty(name = "req2", value = "请求参数2", example = "abc")
    @Length(min = 5, max = 20)
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
