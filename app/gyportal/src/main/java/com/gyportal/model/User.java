package com.gyportal.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * 管理员
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phone;
    private String username;
    private String password;
    private String email;
    private String recently_landed;

//    private List<Role> roles;

}
