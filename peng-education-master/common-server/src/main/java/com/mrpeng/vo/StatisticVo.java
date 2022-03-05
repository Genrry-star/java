package com.mrpeng.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("统计条件实体类")
public class StatisticVo {
    @ApiModelProperty("统计类型")
    private String type;

    @ApiModelProperty("开始时间")
    private String start;

    @ApiModelProperty("结束时间")
    private String end;
}
