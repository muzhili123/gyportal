package com.gyportal.handler;

import com.alibaba.fastjson.JSON;
import com.gyportal.model.AjaxResponseBody;
import com.gyportal.model.Enum.Status;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody.Builder()
                .buildStatus(Status.FAIL).buildMsg("Login Failure!").build();


        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
