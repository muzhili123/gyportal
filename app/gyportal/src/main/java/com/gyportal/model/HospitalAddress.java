package com.gyportal.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * create by lihuan at 19/1/9 10:30
 */
@Data
public class HospitalAddress {

    private Integer address_id;

    private String address_name;

    private String address_name_en;

    private Integer parent_id;

    private List<Hospital> hospitals;

}
