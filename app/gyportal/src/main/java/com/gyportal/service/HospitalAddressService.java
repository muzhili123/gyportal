package com.gyportal.service;

import com.gyportal.model.HospitalAddress;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * create by lihuan at 19/1/9 10:41
 */
public interface HospitalAddressService {

    public List<HospitalAddress> findHospitalAddressByName(String addressName);

    public HospitalAddress getHospitalAddressById(Integer hospitalAddressId);

    @Transactional
    public void insertHospitalAddress(HospitalAddress hospitalAddress);

    @Transactional
    public void updateHospitalAddress(HospitalAddress hospitalAddress);

    public void deleteHospitalAddress(List<String> groupId);

    Integer findByAddressName(String addressName, String addressNameEn);

}
