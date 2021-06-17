package com.hezhu.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.edu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
public interface CommentService extends IService<Comment> {

    //1.根据课程id，分页查询评论
    Map<String, Object> getCommentPage(Page<Comment> commentPage, String courseId);
}
