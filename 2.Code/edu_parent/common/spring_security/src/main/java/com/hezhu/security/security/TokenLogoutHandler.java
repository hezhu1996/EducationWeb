package com.hezhu.security.security;

import com.hezhu.commonutils.R;
import com.hezhu.commonutils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登出业务逻辑类
 */
public class TokenLogoutHandler implements LogoutHandler {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;


    // 构造函数：得到 tokenManager 和 redisTemplate
    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 1.从 header 里面获取 token
        String token = request.getHeader("token");

        if (token != null) {
            // 2.移除
            tokenManager.removeToken(token);
            // 3.从 token 中获取用户名
            String userName = tokenManager.getUserFromToken(token);
            // 4.清空 redis 中用户缓存的权限数据
            redisTemplate.delete(userName);
        }
        // 返回信息
        ResponseUtil.out(response, R.ok());
    }
}