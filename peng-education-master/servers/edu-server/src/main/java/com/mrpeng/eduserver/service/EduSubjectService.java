package com.mrpeng.eduserver.service;

import com.mrpeng.pojo.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrpeng.vo.CourseVo;
import com.mrpeng.vo.SubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-13
 */
public interface EduSubjectService extends IService<EduSubject> {

    void importExcel(MultipartFile file);

    List<SubjectVo> getSubject();



}
