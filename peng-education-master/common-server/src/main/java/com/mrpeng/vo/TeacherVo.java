package com.mrpeng.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("查询讲师功能需要的实体类")
public class TeacherVo {
    @ApiModelProperty("讲师的姓名")
    private  String name;
    @ApiModelProperty("讲师的职称")
    private String level;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
}
