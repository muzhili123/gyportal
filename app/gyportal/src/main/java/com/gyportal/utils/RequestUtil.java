package com.gyportal.utils;

import com.gyportal.service.Impl.NewsServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * create by lihuan at 19/1/16 13:23
 */
public class RequestUtil {

    //请求头
    private static final String CONTENT_LANGUAGE = "en";

    //表名
    private static String TABLE_NAME_EN = "news_en";
    public static String TABLE_NAME_CN = "news";

    public static String getTableName(HttpServletRequest request) {

        //新闻表
        if (CONTENT_LANGUAGE.equals(request.getHeader("content-language"))) {
            return TABLE_NAME_EN;
        } else {
            return TABLE_NAME_CN;
        }
    }

    public static String getContentLanguage(HttpServletRequest request) {
        if (CONTENT_LANGUAGE.equals(request.getHeader("content-language"))) {
            return CONTENT_LANGUAGE;
        } else {
            return "cn";
        }
    }
}
