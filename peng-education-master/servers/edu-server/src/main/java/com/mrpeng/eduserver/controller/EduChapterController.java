package com.mrpeng.eduserver.controller;


import com.mrpeng.pojo.EduChapter;
import com.mrpeng.eduserver.service.EduChapterService;
import com.mrpeng.exception.NormalException;
import com.mrpeng.pojo.R;
import com.mrpeng.vo.ChapterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-15
 */
@RestController
@RequestMapping("/eduserver/edu-chapter")
@Api(tags = "章节的api")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation("根据Id查询章节信息")
    @GetMapping("findById/{id}")
    public R findById(@PathVariable("id") String id){
        EduChapter chapter = eduChapterService.getById(id);
        return R.ok().data("chapter",chapter);
    }
    @ApiOperation("根据Id删除章节信息")
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable("id") String id){
        eduChapterService.deleteById(id);
        return R.ok().message("删除成功");
    }

    @ApiOperation("修改章节信息")
    @PutMapping("update")
    public  R update(@RequestBody EduChapter chapter){
        boolean result = eduChapterService.updateById(chapter);
        if(!result) throw new NormalException("修改失败");
        return R.ok().message("修改成功");
    }


    @ApiOperation("添加章节信息")
    @PostMapping("save")
    public  R save(@RequestBody EduChapter chapter){
        boolean result = eduChapterService.save(chapter);
        if(!result) throw new NormalException("添加失败");
        return R.ok().message("添加成功");
    }

    @ApiOperation("根据课程id，获取章节树")
    @GetMapping("getCourseTree/{id}")
    public R getCourseTree(@PathVariable("id")String id){
        List<ChapterVo> chapterVos = eduChapterService.getCourseTree(id);
        return R.ok().data("chapterTree",chapterVos);

    }

}

