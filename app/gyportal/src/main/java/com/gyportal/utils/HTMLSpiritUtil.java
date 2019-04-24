package com.gyportal.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by lihuan at 18/11/6 14:08
 * 文本处理
 */
public class HTMLSpiritUtil {
    /**
     * 过滤html标签
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr){
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    /**
     * 替换换行和空格
     * @param content
     * @return
     */
    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");

        return content.trim();
    }

    public static String replace(String content) {
        // <p>段落替换为换行
//        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
//        content = content.replaceAll("<br\\s*/?>", "\r\n");

        content = content.replaceAll("\\\\r\\\\n", "");

        content = content.replaceAll("\\\\r", "");
        content = content.replaceAll("\\\\n", "");

        content = content.replaceAll("\r", "");

        content = content.replaceAll("</p>n", "</p>");

        content = content.replaceAll("</div>n", "</div>");

        content = content.replaceAll("\\s{2,}|(\t)*", "");

        // 还原HTML
        // content = HTMLDecoder.decode(content);
        return content;
    }
}
