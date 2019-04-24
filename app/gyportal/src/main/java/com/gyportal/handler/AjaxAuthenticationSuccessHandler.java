package com.gyportal.handler;

import com.alibaba.fastjson.JSON;
import com.gyportal.utils.JwtTokenUtil;
import com.gyportal.model.Enum.Status;
import com.gyportal.service.security.SelfUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.gyportal.model.AjaxResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody.Builder()
                .buildStatus(Status.OK).buildMsg("Login Success!").build();

        SelfUserDetails userDetails = (SelfUserDetails) authentication.getPrincipal();

        String jwtToken = JwtTokenUtil.generateToken(userDetails.getUsername(), 60 * 60 * 24, "_secret");
        responseBody.setToken(jwtToken);

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}

