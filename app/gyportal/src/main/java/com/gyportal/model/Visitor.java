package com.gyportal.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * create by lihuan at 18/12/20 13:25
 */
@Data
@Entity
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String ip;

    private Integer count;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "last_load_address")
    private String lastLoadAddress;

    public Visitor() {
    }

    public Visitor(String ip, Integer count, String lastLoadAddress) {
        this.ip = ip;
        this.count = count;
        this.lastLoadAddress = lastLoadAddress;
    }
}
