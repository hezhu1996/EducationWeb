package com.hezhu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.edu.entity.Comment;
import com.hezhu.edu.mapper.CommentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hezhu.edu.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    //1.根据课程id，分页查询评论
    @Override
    public Map<String, Object> getCommentPage(Page<Comment> commentPage, String courseId) {
        //条件：根据课程id查询
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseId)){
            wrapper.eq("course_id", courseId);
        }

        //数据库查询，数据放入commentPage
        baseMapper.selectPage(commentPage, wrapper);

        //取出数据
        List<Comment> records = commentPage.getRecords();
        long current = commentPage.getCurrent();
        long pages = commentPage.getPages();
        long size = commentPage.getSize();
        long total = commentPage.getTotal();
        boolean hasPrevious = commentPage.hasPrevious();
        boolean hasNext = commentPage.hasNext();

        //封装入map
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
