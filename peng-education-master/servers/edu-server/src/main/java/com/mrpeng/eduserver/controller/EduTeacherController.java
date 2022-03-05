package com.mrpeng.eduserver.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrpeng.eduserver.service.EduCourseService;
import com.mrpeng.pojo.EduCourse;
import com.mrpeng.pojo.EduTeacher;
import com.mrpeng.eduserver.service.EduTeacherService;
import com.mrpeng.exception.IllegalParamException;
import com.mrpeng.pojo.R;
import com.mrpeng.vo.TeacherVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-09
 */
@RestController
@RequestMapping("/eduserver/edu-teacher")
@Api(tags = "讲师的Api")
public class EduTeacherController {

    @Autowired
    private EduTeacherService  eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("findById/{id}")
    @ApiOperation(value ="根据id查询讲师信息",httpMethod = "GET")
    public R findById(@ApiParam(required =true) @PathVariable("id") String id){
       EduTeacher teacher = eduTeacherService.getById(id);
        List<EduCourse> courses = eduCourseService.findCourseByTeacherId(id);
//       int i =10/0;
       return R.ok().data("teacher",teacher).data("courses",courses);
    }
    @GetMapping("findAll")
    @ApiOperation(value ="查询全部讲师信息",httpMethod = "GET")
    public R findAll(){
        List<EduTeacher> list = eduTeacherService.list();
        return R.ok().data("teachers",list);
    }


    @DeleteMapping("deleteById/{id}")
    @ApiOperation(value = "根据讲师id逻辑删除讲师信息")
    public R deleteById(@PathVariable("id") String id){
        boolean result = eduTeacherService.removeById(id);
        if(!result){
            throw new RuntimeException();
        }
        return R.ok().message("删除成功");
    }


    @PostMapping("save")
    @ApiOperation(value = "添加讲师信息")
    public R save(@ApiParam(required = true) @RequestBody EduTeacher teacher){
        boolean result = eduTeacherService.save(teacher);
        if (!result){
        }
        return R.ok().message("添加成功！");
    }
    @PutMapping("update")
    @ApiOperation(value = "修改讲师信息")
    public R update(@RequestBody@ApiParam(required = true) EduTeacher teacher){
        boolean result = eduTeacherService.updateById(teacher);
        if (!result) {
//            return 200;
        }
        return R.ok().message("修改成功！");
    }


    @PostMapping("/findByConditions/{current}/{limit}")
    @ApiOperation("根据条件分页查询老师的信息")
    public R findByConditions(@ApiParam@PathVariable("current") Integer current,
                             @ApiParam@PathVariable("limit") Integer limit,
                             @ApiParam@RequestBody(required = false) TeacherVo condition){
        if(current<0||limit<0){
            throw new IllegalParamException("参数信息不正确");
        }
        Page<EduTeacher> page = eduTeacherService.pageTeacherByConditions(current, limit, condition);
        return R.ok().data("rows",page.getRecords()).data("totals",page.getTotal());

    }

}

