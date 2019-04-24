package com.gyportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * create by lihuan at 18/11/4 09:58
 * 医院
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "name_en")
    private String nameEn;

    private String image;

    private String url;

    private Integer stick;

    @Column(name = "address_id")
    private Integer addressId;
}
