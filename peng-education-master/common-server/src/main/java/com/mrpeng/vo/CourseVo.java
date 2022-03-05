package com.mrpeng.vo;

import com.mrpeng.pojo.EduCourse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("添加课程信息时，需要接受信息的实体类")
public class CourseVo extends EduCourse {
    @ApiModelProperty("课程的名称")
    private String title;

    @ApiModelProperty("课程的描述")
    private String description;

    @ApiModelProperty("课程的一级分类id")
    private String subjectParentId;

    @ApiModelProperty("课程的二级分类id")
    private String subjectId;

    @ApiModelProperty("销量排序")
    private String buyCountSort;

    @ApiModelProperty("课程的时间排序")
    private String gmtCreateSort;

    @ApiModelProperty("价格排序")
    private String priceSort;
}
