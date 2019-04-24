package com.gyportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * create by lihuan at 19/1/10 13:32
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class FriendLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "name_en")
    private String nameEn;

    private String url;

    private Integer effective;
}
