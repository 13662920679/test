package com.example.test.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("CustomAuthenticationEntryPoint:"+e.getMessage());
        //设置响应状态码
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);//403 拒绝访问
        //设置响应数据格式
//        httpServletResponse.setContentType("application/json;charset=utf-8");

        httpServletResponse.sendRedirect("/state/notPermissions.html");
    }
}
