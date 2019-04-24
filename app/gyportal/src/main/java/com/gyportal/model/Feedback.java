package com.gyportal.model;

import lombok.Data;

/**
 * create by lihuan at 18/11/5 14:19
 * 反馈
 */
@Data
public class Feedback {

    private Integer id;
    private String username;
    private String title;
    private String content;
    private String publish_date;
}
