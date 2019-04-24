package com.gyportal.handler;

import com.gyportal.component.TimeUtil;
import com.gyportal.mapper.VisitorMapper;
import com.gyportal.model.Visitor;
import com.gyportal.utils.IPUtils;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * 未使用
 * create by lihuan at 18/12/20 12:32
 */
//@Order(1)
//重点
//@WebFilter(filterName = "vistorCountFilter", urlPatterns = "/*")
public class VisitorCountFilter implements Filter {

    @Resource
    private VisitorMapper visitorMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        // 获取客户端IP地址
//        String clientIp = IPUtils.getClientIp(request);
//
//        updateIp(clientIp);

        //cookie+ip
//        Cookie[] cookies = request.getCookies();
//        Cookie cookie = null;
//
//        //如果用户是第一次访问，那么得到的cookies将是null
//        if (cookies == null) {
//
//            //查询总记录
//            Visitor visitor = visitorMapper.findOne(clientIp);
//            if (visitor == null) {
//                visitorMapper.save(new Visitor(clientIp, 1));
//            } else {
//                visitor.setCount(visitor.getCount() + 1);
//                visitorMapper.save(visitor);
//            }
//
//            //设置用户的访问时间，存储到cookie中，然后发送到客户端浏览器
//            cookie = new Cookie("lastAccessTime", timeUtil.getFormatDateForSix());//创建一个cookie，cookie的名字是lastAccessTime
//            cookie.setMaxAge(24 * 60 * 60);
//            //将cookie对象添加到response对象中，这样服务器在输出response对象中的内容时就会把cookie也输出到客户端浏览器
//            try {
//                ((HttpServletResponse)servletResponse).addCookie(cookie);
//            } catch (Exception e) {
//
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//
//        } else {
//
//            for (int i = 0; i < cookies.length; i++) {
//                cookie = cookies[i];
//                if (!cookie.getName().equals("lastAccessTime")) {
//                    updateIp(clientIp);
//                }
//            }
//        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

//    private void updateIp(String clientIp) {
//        //ip池更新,查询一天之内
//        Integer count = visitorMapper.findVisitorByDay(clientIp);
//        if (count > 0) {
//            return;
//        }
//
//        //一天之内未访问
//        Visitor visitor = new Visitor();
//        visitor.setCount(1);
//        visitor.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//        visitor.setLastLoadAddress(IPUtils.getAddressJson(clientIp));
//        visitorMapper.save(visitor);
//
//    }

}
