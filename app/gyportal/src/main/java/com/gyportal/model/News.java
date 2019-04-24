package com.gyportal.model;

import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;


/**
 * create by lihuan at 18/11/1 10:22
 * 新闻
 */
@Data
public class News {

//    private static final String Table = "news";

    private Integer id;
    private String issuer;
    @NonNull
    private String title;
    private String tabloid;
    @NonNull
    private String content;
    @NonNull
    private String type;
    private String url;
    private String image;
    private Integer page_view;
    private String publish_date;
    private String update_date;
    //1生效，0无效
    private Integer effective;
    private Integer stick;
    private Integer hot;

    private List<Attachment> attachments;

    public News() {
    }

    public static String getNewsEnType(String type) {

        switch (type) {
            case "通知公告" :
                return "Announcement";
            case "中心动态":
                return "Events";
            case "行业资讯":
                return "Industry News";
            case "图片新闻":
                return "Picture News";
            case "技术专题":
                return "Technical Topics";
            default:
                return "Static News";
        }
    }
}
