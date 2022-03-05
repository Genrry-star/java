package com.mrpeng.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("查询课程返回的实体类，树形结构")
public class SubjectVo {
    @ApiModelProperty("课程的id")
    private String id;
    @ApiModelProperty("课程的标题")
    private String title;
    @ApiModelProperty("子标签")
    private List<SubjectVo> children;
}
