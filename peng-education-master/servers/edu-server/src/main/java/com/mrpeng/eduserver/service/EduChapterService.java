package com.mrpeng.eduserver.service;

import com.mrpeng.pojo.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrpeng.vo.ChapterVo;
import com.mrpeng.vo.CourseVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-15
 */
public interface EduChapterService extends IService<EduChapter> {

    void deleteById(String id);

    List<ChapterVo> getCourseTree(String courseId);
}
