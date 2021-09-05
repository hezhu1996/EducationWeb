package com.hezhu.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token管理
 */
@Component
public class TokenManager {

    // token 有效时长
    private long tokenExpiration = 24*60*60*1000;
    // 编码秘钥
    private String tokenSignKey = "123456";

    // 使用 jwt 根据 用户名 生成 token
    public String createToken(String username) {
        // 设置主体部分 username
        String token = Jwts.builder().setSubject(username)
                // 设置有效时长
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                // 设置秘钥
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    // 根据 token 字符串得到用户信息
    public String getUserFromToken(String token) {
        String user = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return user;
    }

    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }
}
