package com.mrpeng.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("返回课程详情实体类")
public class CourseDetailsDto {
    @ApiModelProperty("课程id")
    private String id;
    @ApiModelProperty("课程名字")
    private String title;
    @ApiModelProperty("课程封面")
    private String cover;
    @ApiModelProperty("课时")
    private Integer lessonNum;
    @ApiModelProperty("课程一级分类")
    private String subjectLevelOne;
    @ApiModelProperty("课程二级分类")
    private String subjectLevelTwo;
    @ApiModelProperty("讲师名字")
    private String teacherName;
    @ApiModelProperty("课程价格")
    private String price;
}
