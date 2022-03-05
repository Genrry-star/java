package com.mrpeng.eduserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrpeng.pojo.EduComment;
import com.mrpeng.eduserver.mapper.EduCommentMapper;
import com.mrpeng.eduserver.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-21
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {
    @Autowired
    private EduCommentMapper eduCommentMapper;
    @Override
    public List<EduComment> findCommentByCourseId(String courseId) {
        QueryWrapper<EduComment> wrapper =new QueryWrapper<>();
        wrapper.eq("course_id",courseId).orderByDesc("gmt_create");
        List<EduComment> comments = eduCommentMapper.selectList(wrapper);
        return comments;
    }

    @Override
    public void publishComment(EduComment comment) {
        eduCommentMapper.insert(comment);
    }

    @Override
    public Boolean deleteById(String commentId) {
        int result = eduCommentMapper.deleteById(commentId);
        if(result>0){
            return true;
        }
        return false;
    }
}
