package com.gyportal.model;

import lombok.Data;

import javax.persistence.*;

/**
 * create by lihuan at 18/11/5 15:39
 * 附件
 */
@Data
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "news_id")
    private Integer newsId;

    @Column(name = "table_name")
    private String tableName;

    private String name;

    private String path;

    private Integer effective;
}
