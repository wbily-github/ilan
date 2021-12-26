package com.yjg.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjg.blog.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登录（token失效）访问自定义返回结果
 *
 */
@Component
public class RestAuthoriztionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error("尚未登录，请登录");
        bean.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }






}
