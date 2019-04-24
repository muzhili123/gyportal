package com.gyportal.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * create by lihuan at 18/11/15 17:16
 */
@Data
@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String path;

    private String description;

    public Picture() {
    }

    public Picture(String path, String description) {
        this.path = path;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
