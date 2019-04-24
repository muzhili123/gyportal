package com.gyportal.aspect;

import com.gyportal.controller.NewsController;
import com.gyportal.service.Impl.NewsServiceImpl;
import com.gyportal.service.NewsService;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * create by lihuan at 19/1/14 11:22
 */
@Aspect
@Component
@Order(1)
public class ControllerAspect {

    //请求头
    private static final String CONTENT_LANGUAGE = "en";

    //获取日志记录器
    private Logger logger = Logger.getLogger(getClass());

    //获取线程副本
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.gyportal.controller.*.*(..))")
    public void weblog(){}

    @Before("weblog()")
    public void doBefore(JoinPoint joinPoint){
        //获取请求报文头部元数据
        ServletRequestAttributes requestAttributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        //获取请求对象
        HttpServletRequest request=requestAttributes.getRequest();
        //记录控制器执行前的时间毫秒数
        startTime.set(System.currentTimeMillis());
        logger.info("前置通知执行：----------------------");
        logger.info("language: " + request.getHeader("content-language"));
//        logger.info("url:" + request.getRequestURL());
//        logger.info("method:" + request.getMethod());
//        logger.info("class_method:" + joinPoint.getSignature().getDeclaringTypeName() +
//                "." + joinPoint.getSignature().getName());
//        logger.info("args:" + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret",pointcut = "weblog()")
    public void doAfter(Object ret){
        logger.info("后置通知执行：----------------------");
        logger.info("spend:" + (System.currentTimeMillis()-startTime.get()));
    }

}
