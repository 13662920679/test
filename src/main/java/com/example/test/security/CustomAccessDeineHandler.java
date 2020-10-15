package com.example.test.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeineHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        System.out.println("CustomAccessDeineHandler:"+e.getMessage());
        //设置响应状态码
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401 未授权
        //设置响应数据格式
//        httpServletResponse.setContentType("application/json;charset=utf-8");

        httpServletResponse.sendRedirect("/state/notPermissions.html");
    }
}
