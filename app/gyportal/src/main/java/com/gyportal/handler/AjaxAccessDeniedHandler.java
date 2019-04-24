package com.gyportal.handler;

import com.alibaba.fastjson.JSON;
import com.gyportal.model.AjaxResponseBody;
import com.gyportal.model.Enum.Status;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 已弃用
 */
@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody.Builder()
                .buildStatus(Status.AUTH).buildMsg("Need Authorities!").build();

        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
