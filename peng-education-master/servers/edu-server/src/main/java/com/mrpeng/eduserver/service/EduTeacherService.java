package com.mrpeng.eduserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrpeng.pojo.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrpeng.vo.TeacherVo;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-09
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Page<EduTeacher> pageTeacherByConditions(Integer current, Integer limit, TeacherVo condition);

}
