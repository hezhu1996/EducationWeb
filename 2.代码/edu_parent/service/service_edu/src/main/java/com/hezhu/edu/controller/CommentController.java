package com.hezhu.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hezhu.commonutils.JwtUtils;
import com.hezhu.commonutils.R;
import com.hezhu.commonutils.vo.UcenterMemberVo;
import com.hezhu.edu.client.UcenterClient;
import com.hezhu.edu.entity.Comment;
import com.hezhu.edu.service.CommentService;
import com.hezhu.servicebase.exceptionhandler.HeZhuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    //1.根据课程id，分页查询评论
    @GetMapping("getCommentPage/{page}/{limit}")
    public R getCommentPage(@PathVariable long page, @PathVariable long limit, String courseId) {
        //初始化分页查询
        Page<Comment> commentPage = new Page<>(page, limit);
        //调用service，分页查询
        Map<String, Object> map = commentService.getCommentPage(commentPage, courseId);

        return R.ok().data(map);
    }

    //2.添加评论
    @PostMapping("/auth/addComment")
    public R addComment(HttpServletRequest request, @RequestBody Comment eduComment) {
        //Jwt通过token得到用户id信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //判断用户是否登录
        if (StringUtils.isEmpty(memberId)) {
            throw new HeZhuException(20001, "用户尚未登录");
        }

        eduComment.setMemberId(memberId);

        //远程调用ucenter，根据用户id获取用户信息
        UcenterMemberVo member = ucenterClient.getMemberInfoById(memberId);
        eduComment.setAvatar(member.getAvatar());
        eduComment.setNickname(member.getNickname());

        //保存评论
        commentService.save(eduComment);

        return R.ok();
    }
}

