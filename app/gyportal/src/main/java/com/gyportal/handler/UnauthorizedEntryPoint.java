package com.gyportal.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by lihuan at 18/11/12 15:16
 * 默认情况下登陆失败会跳转页面，这里自定义，同时判断是否ajax请求，是ajax请求则返回json，否则跳转失败页面
 */
@Component
@Slf4j
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (isOptionRequest(request)) {
            //允许跨域
            log.info("浏览器的预请求的处理..");
            response.setStatus(200);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,token");
            return;
        }

        if(isAjaxRequest(request)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
//            response.sendRedirect("/login");
        }

    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }

    public static boolean isOptionRequest(HttpServletRequest request) {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        return false;
    }
}
