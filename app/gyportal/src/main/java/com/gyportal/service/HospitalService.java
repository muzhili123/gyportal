package com.gyportal.service;

import com.gyportal.model.Hospital;
import com.gyportal.model.HospitalAddress;
import com.gyportal.model.PageResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * create by lihuan at 18/11/4 09:59
 */
public interface HospitalService {

    public Hospital getOne(Integer id);

    public PageResult findAll(int pageSize, int pageNum, String language);

    public List<Hospital> findAll();

    @Transactional
    public void insertHospital(Hospital hospital);

    @Transactional
    public void updateHospital(Hospital hospital);

    public void deleteHospital(List<String> groupId);

    public long getHospitalCount();

    public List<Hospital> getStickHospitals(String language, int count);

    Integer getStickHospitalsCount();

    Hospital findHospitalByStick(int stick);

    Integer getMaxPriorityHospital();

    List<Hospital> findHighPriorityHospitals(Integer stick);

    HashMap<String, Object> packHospital(Hospital hospital, HospitalAddress hospitalAddress);

    void cancelStickHospital(Hospital hospital);
}
