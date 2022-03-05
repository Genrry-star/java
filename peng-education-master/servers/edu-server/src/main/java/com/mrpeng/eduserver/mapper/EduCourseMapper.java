package com.mrpeng.eduserver.mapper;

import com.mrpeng.pojo.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrpeng.vo.CourseDetailsVo;
import com.mrpeng.vo.CourseInfoVo;
import com.mrpeng.vo.CourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-13
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CourseDetailsVo getCourseDetails(String id);

    CourseInfoVo getCourseInfo(String courseId);

}
