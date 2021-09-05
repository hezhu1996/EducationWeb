package com.hezhu.security.filter;

import com.hezhu.commonutils.R;
import com.hezhu.commonutils.ResponseUtil;
import com.hezhu.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * 访问过滤器
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    // 构造函数注入
    public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager,RedisTemplate redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
                                                                            throws IOException, ServletException {
        logger.info("\n======== TokenAuthenticationFilter ========= \n"+req.getRequestURI() + "\n");

        if(req.getRequestURI().indexOf("admin") == -1) {
            chain.doFilter(req, res);
            return;
        }

        // 1.获取当前认证成功用户权限信息，默认权限为 null
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            // 根据request，获取用户权限
            authentication = getAuthentication(req);
        } catch (Exception e) {
            ResponseUtil.out(res, R.error());
        }

        // 2.如果有权限信息，放到权限上下文中
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            ResponseUtil.out(res, R.error());
        }
        // 3.放行操作
        chain.doFilter(req, res);
    }

    // 获取用户权限
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 1.从 header 中获取 token
        Enumeration<String> headerNames = request.getHeaderNames();
        String token = request.getHeader("token");
        if(token == null){
            token = request.getHeader("Authorization");
            token = token.replace("Bearer ", "");
        }
        // 2.如果存在 token
        if (token != null && !"".equals(token.trim())) {
            // 1.从 token 获取用户名
            String userName = tokenManager.getUserFromToken(token);
            // 2.从 redis 获取权限列表
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
            // 3.权限列表信息
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            // 4.将 redis 获取的权限列表，转换为集合 Collection<GrantedAuthority>
            for(String permissionValue : permissionValueList) {
                if(StringUtils.isEmpty(permissionValue)) continue;

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(authority);
            }
            // 5.如果当前用户不为空，返回权限
            if (!StringUtils.isEmpty(userName)) {
                return new UsernamePasswordAuthenticationToken(userName, token, authorities);
            }
            return null;
        }
        return null;
    }
}