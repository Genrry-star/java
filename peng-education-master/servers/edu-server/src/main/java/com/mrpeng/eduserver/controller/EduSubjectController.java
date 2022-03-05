package com.mrpeng.eduserver.controller;


import com.mrpeng.eduserver.service.EduSubjectService;
import com.mrpeng.pojo.R;
import com.mrpeng.vo.SubjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-13
 */
@RestController
@RequestMapping("/eduserver/edu-subject")
@Api(tags = "课程的导入的api")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;
    @PostMapping("importExcel")
    @ApiOperation("导入课程列表")
    public R importExcel(MultipartFile file){
        eduSubjectService.importExcel(file);
        return R.ok();
    }

    @GetMapping("getSubject")
    @ApiOperation("获取课程树")
    public R getSubject(){
        List<SubjectVo> subject = eduSubjectService.getSubject();
        return R.ok().data("subject",subject);
    }

}

