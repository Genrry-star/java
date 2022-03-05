package com.mrpeng.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("查询轮播图信息需要的实体类")
public class BannerVo {
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("开始时间")
    private String start;
    @ApiModelProperty("结束时间")
    private String end;

}
