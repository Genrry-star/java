package com.mrpeng.eduserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrpeng.pojo.EduTeacher;
import com.mrpeng.eduserver.mapper.EduTeacherMapper;
import com.mrpeng.eduserver.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrpeng.vo.TeacherVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-09
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduTeacherMapper teacherMapper;
    @Override
    public Page<EduTeacher> pageTeacherByConditions(Integer current, Integer limit, TeacherVo condition) {
        Page<EduTeacher> page =new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper =new QueryWrapper<>();
        if(condition!=null){
            if(StringUtils.isNotEmpty(condition.getName())){
                wrapper.like("name",condition.getName());
            }
            if(StringUtils.isNotEmpty(condition.getLevel())){
                wrapper.eq("level",condition.getLevel());
            }
            if(StringUtils.isNotEmpty(condition.getStartDate())){
                wrapper.ge("gmt_create",condition.getStartDate());
            }
            if(StringUtils.isNotEmpty(condition.getEndDate())){
                wrapper.le("gmt_modified",condition.getEndDate());
            }
            wrapper.orderByDesc("sort");
            page =teacherMapper.selectPage(page, wrapper);
        }
        return page;
    }
}
