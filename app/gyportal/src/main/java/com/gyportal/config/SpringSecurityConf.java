package com.gyportal.config;

import com.gyportal.handler.*;
import com.gyportal.service.security.SelfUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author: lihuan
 * Describe: SpringSecurity配置
 */
@Configuration
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;  //  未登陆时返回 JSON 格式的数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler;  // 登录成功返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler;  //  登录失败返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxLogoutSuccessHandler logoutSuccessHandler;  // 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）

    @Autowired
    UnauthorizedEntryPoint unauthorizedEntryPoint; // 无权访问返回的 JSON 格式数据给前端

    @Autowired
    SelfUserDetailsService userDetailsService; // 自定义user

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter; // JWT 拦截器

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 安全认证
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(new MD5PasswordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 去掉 CSRF
        http.csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用 JWT，关闭token
                .and()

                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)

                .and()
                .authorizeRequests()

                .antMatchers("/*","/static/*","/attachment/*",
                        "/getVisitorCount","/increaseVisitorCount","/countVisitor",

                        "/resource/findFriendLink","/resource/findStaticArticle",

                        "/news/findAll", "/news/getBanners", "/news/findAllByType", "/news/getLatest",
                        "/news/getLatestByType", "/news/matchNews", "/news/increasePV","/news/getNewsById",
                        "/news/getHotNews","/news/fetchNearNewsById",

                        "/hospital/*")
                .permitAll()

                .anyRequest()
                .access("@rbacauthorityservice.hasPermission(request,authentication)") // RBAC 动态 url 认证

                .and()
                .formLogin()  //开启登录
                .successHandler(authenticationSuccessHandler) // 登录成功
                .failureHandler(authenticationFailureHandler) // 登录失败
                .permitAll()

                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();

        // 记住我
        http.rememberMe().rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService).tokenValiditySeconds(60 * 60 * 24);

        http.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint);
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // JWT Filter

    }
}
