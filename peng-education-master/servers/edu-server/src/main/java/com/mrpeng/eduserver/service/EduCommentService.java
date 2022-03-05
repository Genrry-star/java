package com.mrpeng.eduserver.service;

import com.mrpeng.pojo.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-21
 */
public interface EduCommentService extends IService<EduComment> {
    List<EduComment> findCommentByCourseId(String courseId);

    void publishComment(EduComment comment);

    Boolean deleteById(String commentId);
}
