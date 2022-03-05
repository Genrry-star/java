package com.mrpeng.eduserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrpeng.pojo.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrpeng.pojo.EduTeacher;
import com.mrpeng.vo.CourseDetailsVo;
import com.mrpeng.vo.CourseInfoVo;
import com.mrpeng.vo.CourseVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-13
 */
public interface EduCourseService extends IService<EduCourse> {
    String save(CourseVo course);

    CourseVo finById(String id);

    void update(CourseVo courseVo);

    Page pageFindByConditions(Integer current, Integer limit, CourseVo course);

    CourseDetailsVo getDetails(String id);

    void publishCourse(String courseId);

    List<EduCourse> findHotCourse();

    List<EduTeacher> findHotTeacher();

    CourseInfoVo findCourseInfo(String courseId);

    List<EduCourse> findCourseByTeacherId(String teacherId);
}
