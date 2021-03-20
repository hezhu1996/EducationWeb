package com.hezhu.educenter.controller;


import com.hezhu.commonutils.JwtUtils;
import com.hezhu.commonutils.R;
import com.hezhu.commonutils.vo.UcenterMemberOrder;
import com.hezhu.commonutils.vo.UcenterMemberVo;
import com.hezhu.educenter.entity.UcenterMember;
import com.hezhu.educenter.entity.vo.RegisterVo;
import com.hezhu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
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

    //4.根据用户id查询用户信息
    @PostMapping("/getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable String memberId) {
        UcenterMember member = memberService.getById(memberId);
        UcenterMemberVo memberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(member, memberVo);

        return memberVo;
    }

    //5.service_order：根据用户id，获取全部用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberOrder);

        return ucenterMemberOrder;
    }

    //6.后台统计：查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister", count);
    }

}






































