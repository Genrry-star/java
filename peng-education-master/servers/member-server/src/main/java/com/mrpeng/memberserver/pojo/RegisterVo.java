package com.mrpeng.memberserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("注册用户所需要的封装信息")
public class RegisterVo {
    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("密码")
    private String password;
}
