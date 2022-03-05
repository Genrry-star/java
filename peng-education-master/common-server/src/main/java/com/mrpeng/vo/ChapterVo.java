package com.mrpeng.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("返回章节信息的实体类，结构类似于树形结构")
public class ChapterVo {
    @ApiModelProperty("章节id")
    private String id;
    @ApiModelProperty("章节标题")
    private String title;
    @ApiModelProperty("小姐信息")
    private List<SectionVo> section =new ArrayList<>();
}
