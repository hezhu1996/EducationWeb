package com.hezhu.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hezhu.commonutils.JwtUtils;
import com.hezhu.commonutils.MD5;
import com.hezhu.educenter.entity.UcenterMember;
import com.hezhu.educenter.entity.vo.RegisterVo;
import com.hezhu.educenter.mapper.UcenterMemberMapper;
import com.hezhu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hezhu.servicebase.exceptionhandler.HeZhuException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    //1.用户登录
    @Override
    public String login(UcenterMember member) {
        //1.获取登陆手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //2.手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new HeZhuException(20001, "登录失败");
        }

        //3.判断手机号是否正确：根据手机号，查找数据库中对应用户
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);

        //4.判断查询对象是否为空
        if (mobileMember == null) {//没有手机号
            throw new HeZhuException(20001, "登录失败，没有用户名");
        }

        //5.判断密码是否正确
        //把输入密码进行MD5加密，再和数据库密码进行比较
        if (!MD5.encrypt(password).equals((mobileMember.getPassword()))) {
            throw new HeZhuException(20001, "登录失败，密码错误");
        }

        //6.判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new HeZhuException(20001, "登录失败，用户已被禁用");
        }

        //7.登录成功，生成token字符串,使用jwt
        //member：前端传过来只有用户名密码，没有id和昵称
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    //2.用户注册
    @Override
    public void register(RegisterVo registerVo) {
        //1.获取注册数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //2.非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new HeZhuException(20001, "注册失败");
        }

        //3.阿里云短息验证：略过

        //4.判断手机号是否重复，表里面存在相同手机号，则不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new HeZhuException(20001, "手机号重复");
        }

        //5.加入数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password)); //MD5加密
        member.setIsDisabled(false);
        member.setAvatar("https://edu-hezhu.oss-us-east-1.aliyuncs.com/2021/02/23/90e64159dae14c84b4de257db514484ffile.png");

        baseMapper.insert(member);

    }




























}
