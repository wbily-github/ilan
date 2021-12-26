package com.yjg.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHear = request.getHeader(tokenHeader);
        log.info("^^^^^^^^^^^^^^^^^^^authHear^"+authHear);
        //判断存在token
        if (null != authHear && authHear.startsWith(tokenHead)) {
            String authTken = authHear.substring(tokenHead.length());
            String username = jwtTokenUtil.getUsername(authTken);
            log.info("%%%%%%%%%%%%%" + username);
            //token存在没登录
            if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                //token有效，重新设置用户对象
                if (jwtTokenUtil.isTokenUsing(tokenHead, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}




