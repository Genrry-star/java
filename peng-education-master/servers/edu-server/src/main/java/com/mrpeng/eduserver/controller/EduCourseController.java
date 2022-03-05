package com.mrpeng.eduserver.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrpeng.dto.CourseDetailsDto;
import com.mrpeng.eduserver.service.EduChapterService;
import com.mrpeng.eduserver.service.EduCourseService;
import com.mrpeng.exception.NormalException;
import com.mrpeng.pojo.EduCourse;
import com.mrpeng.pojo.EduSubject;
import com.mrpeng.pojo.EduTeacher;
import com.mrpeng.pojo.R;
import com.mrpeng.vo.ChapterVo;
import com.mrpeng.vo.CourseDetailsVo;
import com.mrpeng.vo.CourseInfoVo;
import com.mrpeng.vo.CourseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-13
 */
@RestController
@RequestMapping("/eduserver/edu-course")
@Api(tags = "课程的api")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @GetMapping("demo")
    public String demo(){
        return "hello";
    }

    @PostMapping("save")
    @ApiOperation("添加课程信息，并返回课程的id")
    public R save(@RequestBody CourseVo course){
        String id = eduCourseService.save(course);
        return R.ok().data("courseId",id);
    }

    @GetMapping("findById/{id}")
    @ApiOperation("根据id查询课程信息")
    public R findById(@PathVariable("id") String id){
        CourseVo courseVo = eduCourseService.finById(id);
        System.out.println(courseVo);
        return R.ok().data("course",courseVo);
    }
    @ApiOperation("根据id删除课程信息，逻辑删除")
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable("id")String id){
        boolean result = eduCourseService.removeById(id);
        if(!result) throw new NormalException("删除失败");
        return R.ok().message("删除成功");
    }

    @PutMapping("update")
    @ApiOperation("修该课程信息")
    public R update(@RequestBody CourseVo courseVo){
        eduCourseService.update(courseVo);
        return R.ok();
    }

    @ApiOperation("查询所有的课程信息")
    @PostMapping("findByCondition/{current}/{limit}")
    public R findByCondition(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody CourseVo course){
        Page<EduCourse> page= eduCourseService.pageFindByConditions(current, limit, course);
        return R.ok().data("rows",page.getRecords()).data("totals",page.getTotal());
    }

    @ApiOperation("查询课程详细信息")
    @GetMapping("getDetails/{id}")
    public R getDetails(@PathVariable("id")String courseId){
        CourseDetailsVo details = eduCourseService.getDetails(courseId);
        return R.ok().data("details",details);
    }

    @ApiOperation("发布课程")
        @GetMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable("id")String courseId){
        eduCourseService.publishCourse(courseId);
        return R.ok().message("发布成功！！");
    }

    @ApiOperation("查询热门课程")
    @GetMapping("getIndexCourseTeacher")
    public R getHotCourse(){
        List<EduCourse> courses = eduCourseService.findHotCourse();
        List<EduTeacher> teachers =eduCourseService.findHotTeacher();
        return R.ok().data("hotCourse",courses).data("hotTeacher",teachers);
    }

    @ApiOperation("根据id查询课程详细信息，返回CourseInfoVo对象")
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable("id")String courseId){
        CourseInfoVo courseInfo = eduCourseService.findCourseInfo(courseId);
        List<ChapterVo> courseTree = eduChapterService.getCourseTree(courseId);
        return R.ok().data("courseInfo",courseInfo).data("chapterTree",courseTree);
    }

    @ApiOperation("服务远程调用获取课程信息")
    @GetMapping("getCourseDetails/{id}")
    public CourseDetailsDto getCourse(@PathVariable("id")String courseId){
        CourseInfoVo courseInfo = eduCourseService.findCourseInfo(courseId);
        CourseDetailsDto detailsDto =new CourseDetailsDto();
        BeanUtils.copyProperties(courseInfo,detailsDto);
        return detailsDto;
    }


    @ApiOperation("购买量增加")
    @GetMapping("addBuyCount/{courseId}")
    public void addBuyCount(@PathVariable("courseId")String courseId){
        //购买数量增加
        UpdateWrapper<EduCourse> courseWrapper =new UpdateWrapper<>();
        courseWrapper.setSql("buy_count=buy_count+1");
        courseWrapper.eq("id",courseId);
        eduCourseService.update(courseWrapper);
    }

}

