package com.mrpeng.eduserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrpeng.eduserver.mapper.EduCourseDescriptionMapper;
import com.mrpeng.eduserver.mapper.EduTeacherMapper;
import com.mrpeng.exception.NormalException;
import com.mrpeng.pojo.EduCourseCollect;
import com.mrpeng.pojo.EduCourse;
import com.mrpeng.eduserver.mapper.EduCourseMapper;
import com.mrpeng.eduserver.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrpeng.pojo.EduCourseDescription;
import com.mrpeng.pojo.EduTeacher;
import com.mrpeng.vo.CourseDetailsVo;
import com.mrpeng.vo.CourseInfoVo;
import com.mrpeng.vo.CourseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAEncTSEnc;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-13
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseMapper eduCourseMapper;
    @Autowired
    private EduCourseDescriptionMapper descriptionMapper;
    @Autowired
    private EduTeacherMapper teacherMapper;
    @Override
    public String save(CourseVo course) {
        EduCourse eduCourse =new CourseVo();
        EduCourseDescription courseDescription =new EduCourseDescription();
        BeanUtils.copyProperties(course,eduCourse);
        BeanUtils.copyProperties(course,courseDescription);
        int insertCourse = eduCourseMapper.insert(eduCourse);
//        System.out.println("test------------------:"+courseDescription);
        courseDescription.setId(eduCourse.getId());
        int insertDescription = descriptionMapper.insert(courseDescription);

        if(insertCourse<1||insertDescription<1){
            throw new NormalException("服务器错误！！");
        }
//        System.out.println(eduCourse.getId());
        return eduCourse.getId();
    }

    @Override
    public CourseVo finById(String id) {
        EduCourse eduCourse = eduCourseMapper.selectById(id);
        EduCourseDescription courseDescription = descriptionMapper.selectById(id);
        CourseVo courseVo=new CourseVo();
        BeanUtils.copyProperties(eduCourse,courseVo);
        if(courseDescription!=null){
            BeanUtils.copyProperties(courseDescription,courseVo);
        }else{
            courseVo.setDescription("");
        }
        return courseVo;

    }

    @Override
    public void update(CourseVo courseVo) {
        EduCourse eduCourse =new CourseVo();
        EduCourseDescription courseDescription =new EduCourseDescription();
        BeanUtils.copyProperties(courseVo,eduCourse);
        BeanUtils.copyProperties(courseVo,courseDescription);
        eduCourseMapper.updateById(eduCourse);
        descriptionMapper.updateById(courseDescription);
    }

    @Override
    public Page<EduCourse> pageFindByConditions(Integer current, Integer limit, CourseVo course) {
        Page<EduCourse> page =new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper =new QueryWrapper<>();
        if(course!=null){
            if(StringUtils.isNotEmpty(course.getTitle())){
                wrapper.like("title",course.getTitle());
            }
            if(StringUtils.isNotEmpty(course.getStatus())){
                wrapper.eq("status",course.getStatus());
            }
            if(StringUtils.isNotEmpty(course.getSubjectParentId())){
                wrapper.eq("subject_parent_id",course.getSubjectParentId());
            }
            if(StringUtils.isNotEmpty(course.getSubjectId())){
                wrapper.eq("subject_id",course.getSubjectId());
            }
            if(StringUtils.isNotEmpty(course.getBuyCountSort())){
                wrapper.orderByDesc("buy_count");
            }
            if(StringUtils.isNotEmpty(course.getGmtCreateSort())){
                wrapper.orderByDesc("gmt_create");
            }
            if(StringUtils.isNotEmpty(course.getPriceSort())){
                wrapper.orderByDesc("price");
            }
        }
//        wrapper.orderByDesc("gmt_create");
        page = eduCourseMapper.selectPage(page, wrapper);
        return page;
    }

    @Override
    public CourseDetailsVo getDetails(String id) {
        return eduCourseMapper.getCourseDetails(id);
    }

    @Override
    @CacheEvict(cacheNames = "course",key = "'courseInfo'")
    public void publishCourse(String courseId) {
        EduCourse course =new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");
        int result = eduCourseMapper.updateById(course);
        if(result<1){
            throw new NormalException("修改失败");
        }
    }
    @Cacheable(cacheNames = "course",key = "'hotCourse'")
    @Override
    public List<EduCourse> findHotCourse() {
        QueryWrapper<EduCourse> wrapper =new QueryWrapper<>();
        wrapper.orderByAsc("buy_count");
        wrapper.last("limit 8");
        List<EduCourse> courses = eduCourseMapper.selectList(wrapper);
        return courses;
    }
    @Cacheable(cacheNames = "teacher",key = "'hotTeacher'")
    @Override
    public List<EduTeacher> findHotTeacher() {
        QueryWrapper<EduTeacher> wrapper =new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        wrapper.last("limit 4");
        List<EduTeacher> teachers =teacherMapper.selectList(wrapper);
        return teachers;
    }
//    @Cacheable(cacheNames = "course",key = "#courseId")
    @Override
    public CourseInfoVo findCourseInfo(String courseId) {
        CourseInfoVo courseInfo = eduCourseMapper.getCourseInfo(courseId);
        return courseInfo;
    }

    @Override
    public List<EduCourse> findCourseByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> wrapper =new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courses = eduCourseMapper.selectList(wrapper);
        return courses;
    }


}
