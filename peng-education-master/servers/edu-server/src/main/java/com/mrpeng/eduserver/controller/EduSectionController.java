package com.mrpeng.eduserver.controller;


import com.mrpeng.eduserver.service.EduSectionService;
import com.mrpeng.exception.NormalException;
import com.mrpeng.pojo.EduChapter;
import com.mrpeng.pojo.EduSection;
import com.mrpeng.pojo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-15
 */
@RestController
@RequestMapping("/eduserver/edu-section")
@Api(tags ="小节的api")
public class EduSectionController {
    @Autowired
    private EduSectionService eduSectionService;

    @ApiOperation("根据id查询小节的信息")
    @GetMapping("findById/{id}")
    public R findById(@PathVariable("id")String id){
        EduSection section = eduSectionService.getById(id);
        return R.ok().data("section",section);
    }

    @ApiOperation("根据Id删除小节信息")
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable("id") String id){
        boolean result= eduSectionService.removeById(id);
        if(!result) throw new NormalException("删除失败");
        return R.ok().message("删除成功");
    }

    @ApiOperation("修改小节信息")
    @PutMapping("update")
    public  R update(@RequestBody EduSection section){
        boolean result = eduSectionService.updateById(section);
        if(!result) throw new NormalException("修改失败");
        return R.ok().message("修改成功");
    }


    @ApiOperation("添加小节信息")
    @PostMapping("save")
    public  R save(@RequestBody EduSection section){
        boolean result = eduSectionService.save(section);
        if(!result) throw new NormalException("添加失败");
        return R.ok().message("添加成功");
    }

}

