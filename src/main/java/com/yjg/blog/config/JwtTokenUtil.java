package com.yjg.blog.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author yujiangong
 * @Title:
 * @Package
 * @Description:
 * @date 2021/12/810:43
 */
@Slf4j
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * 生成·token
     *
     * @param userDetails
     * @return
     */
    public String getToken(UserDetails userDetails) {
        Map<String, Object> clains = new HashMap<>();
        clains.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        clains.put(CLAIM_KEY_CREATED, new Date());
        return getToken1(clains);
    }

    /**
     * 通过token获取用户名
     *
     * @param token
     * @return
     */
    public String getUsername(String token) {
        log.info("$$$$$$$$$$$$$$$$$$$$" + token);
        String username = "";
        try {
            Claims claim = getClaimsFromToken(token);
            username = claim.getSubject();
        } catch (Exception e) {
            log.error("这他喵是token？", e);
        }
        return username;
    }

    /**
     * token转荷载
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        log.info("@tokenn##############" + token);
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            e.getClaims();
            log.error("转不了就是转不了" + e.getClaims());
        }
        return claims;
    }

    /**
     * 判断token是否有效
     *
     * @return
     */
    public Boolean isTokenUsing(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenUnse(token);
    }

    /**
     * 是否可以被刷新（是否已经过期）
     *
     * @param token
     * @return
     */
    public boolean isRefresh(String token) {
        return !isTokenUnse(token);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return getToken1(claims);
    }

    /**
     * 判断token是否已经失效
     *
     * @param token
     * @return
     */
    private boolean isTokenUnse(String token) {
        //获取token失效时间
        Date date = getClaimsFromToken(token).getExpiration();
        return date.before(new Date());
    }

    /**
     * 根据荷载生成jwt token
     *
     * @param claims
     * @return
     */
    private String getToken1(Map<String, Object> claims) {
        log.info("bulabulabulabulabulabulabulabulabulabulabulabula000" + claims.toString());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpiratoinDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成token失效时间
     *
     * @return
     */
    private Date generateExpiratoinDate() {
        return new Date((new Date()).getTime() + expiration*1000);
    }

}
