package com.mrpeng.eduserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrpeng.dto.MemberDto;
import com.mrpeng.eduserver.feign.MemberFeign;
import com.mrpeng.eduserver.service.EduCommentService;
import com.mrpeng.eduserver.service.EduCourseService;
import com.mrpeng.pojo.EduComment;
import com.mrpeng.pojo.EduCourse;
import com.mrpeng.pojo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-21
 */
@RestController
@RequestMapping("/eduserver/edu-comment")
public class EduCommentController {
    @Autowired
    private EduCommentService eduCommentService;
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private MemberFeign memberFeign;

    @ApiOperation("根据课程id查询，评论信息")
    @GetMapping("getComments/{id}")
    public R getComments(@PathVariable("id")String courseId){
        List<EduComment> comments = eduCommentService.findCommentByCourseId(courseId);
        return R.ok().data("commentList",comments);

    }

    @ApiOperation("删除评论")
    @DeleteMapping("deleteCommentById/{id}")
    public R deleteCommentById(@PathVariable("id")String id){
        Boolean result = eduCommentService.deleteById(id);
        if(result) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("添加评论")
    @PostMapping("saveComment/{courseId}")
    public R save(@PathVariable("courseId")String courseId,@RequestBody EduComment comment){
        QueryWrapper<EduCourse> courseQueryWrapper =new QueryWrapper<>();
        courseQueryWrapper.eq("id",courseId);
        EduCourse course = eduCourseService.getOne(courseQueryWrapper);
        //获取会员信息，微服务远程调用
        System.out.println("comment = " + comment);
        MemberDto member = memberFeign.getMemberById(comment.getMemberId());

        EduComment params=new EduComment();
        params.setCourseId(courseId);
        params.setTeacherId(course.getTeacherId());
        params.setMemberId(member.getId());
        params.setNickname(member.getNickname());
        params.setAvatar(member.getAvatar());
        params.setContent(comment.getContent());

        boolean save = eduCommentService.save(params);
        if (save) {
            return R.ok().message("发表成功！");
        }
        return R.error().message("发表失败！");


    }
}

