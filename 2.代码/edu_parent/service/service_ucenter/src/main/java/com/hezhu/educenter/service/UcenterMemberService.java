package com.hezhu.educenter.service;

import com.hezhu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hezhu.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-09
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //1.用户登录
    String login(UcenterMember member);

    //2.用户注册
    void register(RegisterVo registerVo);
}
