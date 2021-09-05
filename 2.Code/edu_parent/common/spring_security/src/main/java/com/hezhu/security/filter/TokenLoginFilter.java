package com.hezhu.security.filter;
import com.hezhu.commonutils.R;
import com.hezhu.commonutils.ResponseUtil;
import com.hezhu.security.entity.SecurityUser;
import com.hezhu.security.entity.User;
import com.hezhu.security.security.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    // 官方认证(权限)管理
    private AuthenticationManager authenticationManager;
    // token 的操作
    private TokenManager tokenManager;
    // redis 的操作
    private RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        // 构造注入
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        // post 提交
        this.setPostOnly(false);
        // 登录路径 + 提交方式
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
    }

    // 1.获取表单提交的用户名和密码
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            // 获取表单提交数据
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);

            // 认证！返回用户权限
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>()));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录成功，调用此方法
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
                                            throws IOException, ServletException {

        System.out.println("===================登录成功======================");

        // 1.获取认证成功之后的用户信息
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        // 2.根据用户名，生成token
        String token = tokenManager.createToken(user.getCurrentUserInfo().getUsername());
        // 3.把 <用户名> 和 <用户权限列表> 放到 redis
        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(), user.getPermissionValueList());

        ResponseUtil.out(res, R.ok().data("token", token));
    }

    /**
     * 登录失败，调用此方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
                                            throws IOException, ServletException {

        System.out.println("===================登录失败======================");
        ResponseUtil.out(response, R.error());
    }
}











