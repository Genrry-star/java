package com.mrpeng.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Excel表格对象,注意属性的第一个字母不能大写")
public class ExcelData {
    @ApiModelProperty("一级课程")
    @ExcelProperty("一级课程")
    private String oneSubject;
    @ApiModelProperty("二级课程")
    @ExcelProperty("二级课程")
    private String twoSubject;
}
