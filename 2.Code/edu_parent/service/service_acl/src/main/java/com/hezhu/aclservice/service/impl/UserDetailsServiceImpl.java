package com.hezhu.aclservice.service.impl;

import com.hezhu.aclservice.entity.User;
import com.hezhu.aclservice.service.PermissionService;
import com.hezhu.aclservice.service.UserService;
import com.hezhu.security.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 自定义userDetailsService - 认证用户详情
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /***
     * 根据用户名获取用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.从数据库中取出 <用户信息>
        User user = userService.selectByUsername(username);

        // 2.判断用户是否存在
        if (null == user){
            //throw new UsernameNotFoundException("用户名不存在！");
        }
        // 3.数据库查出的 User，对拷入 secuirty.User 中
        com.hezhu.security.entity.User curUser = new com.hezhu.security.entity.User();
        BeanUtils.copyProperties(user,curUser);
        // 4.根据 User 的 Id，查询该用户权限
        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        // 5.将权限信息放入 SecurityUser ---> TokenLoginFilter 登陆成功使用，向 redis 中给当前用户写入权限
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);

        return securityUser;
    }
}
