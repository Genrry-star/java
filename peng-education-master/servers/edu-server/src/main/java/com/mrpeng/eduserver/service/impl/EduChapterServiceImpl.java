package com.mrpeng.eduserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrpeng.eduserver.mapper.EduSectionMapper;
import com.mrpeng.exception.NormalException;
import com.mrpeng.pojo.EduChapter;
import com.mrpeng.eduserver.mapper.EduChapterMapper;
import com.mrpeng.eduserver.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrpeng.pojo.EduSection;
import com.mrpeng.vo.ChapterVo;
import com.mrpeng.vo.CourseVo;
import com.mrpeng.vo.SectionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-15
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduChapterMapper eduChapterMapper;
    @Autowired
    private EduSectionMapper eduSectionMapper;
    @Override
    public void deleteById(String id) {
        try {
            QueryWrapper wrapper =new QueryWrapper();
            wrapper.eq("chapter_id",id);
            eduSectionMapper.delete(wrapper);
            eduChapterMapper.deleteById(id);
        } catch (Exception e) {
            throw new NormalException("删除失败");
        }
    }

    @Override
    public List<ChapterVo> getCourseTree(String courseId) {
        //查询该课程的所有章节
        QueryWrapper<EduChapter> chapterWrapper =new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = eduChapterMapper.selectList(chapterWrapper);
        //查询该课程下的所有小节
        QueryWrapper<EduSection> sectionWrapper =new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduSection> eduSections = eduSectionMapper.selectList(sectionWrapper);
        //返回的集合
        List<ChapterVo> list =new ArrayList<>();
        //对课程的章节和小节进行封装程CourseVo
        for (EduChapter chapter : eduChapters) {
            ChapterVo chapterVo =new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            for (EduSection section : eduSections) {
                if(StringUtils.equals(chapter.getId(),section.getChapterId())){
                    SectionVo vo=new SectionVo();
                    BeanUtils.copyProperties(section,vo);
                    chapterVo.getSection().add(vo);
                }
            }
            list.add(chapterVo);
        }
        return list;
    }


}
