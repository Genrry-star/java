package com.mrpeng.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value = "返回对象实体类")
public class R {
    @ApiModelProperty(value = "返回信息")
    private String msg;
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data =new HashMap<>();
    private R() {
    }
    public static R ok(){
        R r =new R();
        r.setCode(20000);
        r.setMsg("操作成功");
        r.setSuccess(true);
        return r;
    }
    public static R error(){
        R r =new R();
        r.setCode(20001);
        r.setMsg("操作失败");
        r.setSuccess(false);
        return r;
    }
    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
    public R message(String error){
        this.msg =error;
        return this;
    }
    public R code(Integer statusCode){
        this.code =statusCode;
        return this;
    }
    public R success(Boolean bool){
        this.success =bool;
        return this;
    }

    public R data(Map<String, Object> userInfo) {
        this.data =userInfo;
        return this;
    }
}
