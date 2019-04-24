package com.gyportal.handler;

import com.alibaba.fastjson.JSON;
import com.gyportal.model.AjaxResponseBody;
import com.gyportal.model.Enum.Status;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody.Builder()
                .buildStatus(Status.OK).buildMsg("Login Success!").build();

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
