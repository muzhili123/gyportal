package com.gyportal.model;

import lombok.Data;

/**
 * create by lihuan at 18/11/1 16:51
 * 角色
 */
@Data
public class Role {

    private int id;

    private String name;

    public Role(){

    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
