package com.hezhu.educenter.controller;


import com.hezhu.commonutils.JwtUtils;
import com.hezhu.commonutils.R;
import com.hezhu.educenter.entity.UcenterMember;
import com.hezhu.educenter.entity.vo.RegisterVo;
import com.hezhu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //1.用户登录
    @PostMapping("login")
    public R loginUser(@RequestBody(required = false) UcenterMember member) {
        //1.调用service实现登录，返回token使用jwt生成
        String token = memberService.login(member);

        return R.ok().data("token", token);
    }

    //2.用户注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    //3.根据token，获取用户信息：前端头像显示
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //1.调用jwt工具类方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库：根据用户id获取用户全部信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }
}






































