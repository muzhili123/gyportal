package com.gyportal.model;

import lombok.Data;

/**
 * create by lihuan at 18/11/1 10:51
 * 公告
 */
@Data
public class Announcement {

    private Integer id;

    private String issuer;

    private String title;

    private String content;

    private String publish_date;

    private String update_date;

}
