package com.gyportal.service.Impl;

import com.gyportal.mapper.HospitalMapper;
import com.gyportal.model.Hospital;
import com.gyportal.model.HospitalAddress;
import com.gyportal.model.PageResult;
import com.gyportal.repository.mybatis.HospitalAddressReposity;
import com.gyportal.service.HospitalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * create by lihuan at 18/11/4 10:11
 */
@Service(value = "hospitalServiceImpl")
public class HospitalServiceImpl implements HospitalService {

    @Resource
    private HospitalMapper hospitalMapper;

    @Resource
    private HospitalAddressReposity hospitalAddressReposity;

    @Override
    public Hospital getOne(Integer id) {
        return hospitalMapper.getOne(id);
    }

    @Override
    public PageResult findAll(int pageSize, int pageNum, String language) {

        List<HospitalAddress> hospitalAddresses = hospitalAddressReposity.findAllByPage(pageSize * (pageNum - 1), pageSize, language);

        if (hospitalAddresses.size() != 0) {
            System.out.println(hospitalAddresses.size() + "----------");
            for (HospitalAddress hospitalAddress : hospitalAddresses) {

                if (language.equals("en")) {
                    hospitalAddress.setHospitals(hospitalMapper.findAllByAddressIdAndLanguage(hospitalAddress.getAddress_id()));
                } else {
                    hospitalAddress.setHospitals(hospitalMapper.findAllByAddressId(hospitalAddress.getAddress_id()));
                }

            }
        }

        PageResult pageResult = new PageResult();

        pageResult.setContent(hospitalAddresses);
        pageResult.setTotalElements(hospitalAddressReposity.count(language));
        pageResult.setNumber(pageNum);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages();
        pageResult.setNumberOfElements(hospitalAddresses.size());

        return pageResult;
    }

    @Override
    public List<Hospital> findAll() {
        return hospitalMapper.findAll();
    }

    @Override
    public void insertHospital(Hospital hospital) {
        hospitalMapper.save(hospital);
    }

    @Override
    public void updateHospital(Hospital hospital) {
        hospitalMapper.save(hospital);
    }

    @Override
    public void deleteHospital(List<String> groupId) {
        List<Hospital> hospitals = new ArrayList<>();
        Hospital hospital = new Hospital();
        for (String str : groupId) {
            hospital.setId(Integer.valueOf(str));
            hospitals.add(hospital);
        }
        hospitalMapper.delete(hospitals);
    }

    @Override
    public long getHospitalCount() {
        return hospitalMapper.count();
    }

    @Override
    public List<Hospital> getStickHospitals(String language, int count) {

        if (language.equals("en")) {
            hospitalMapper.getStickHospitalsByLanguage(count);
        }

        return hospitalMapper.getStickHospitals(count);
    }

    @Override
    public Integer getStickHospitalsCount() {
        return hospitalMapper.getStickHospitalsCount();
    }

    @Override
    public Hospital findHospitalByStick(int stick) {
        return hospitalMapper.findHospitalByStick(stick);
    }

    @Override
    public Integer getMaxPriorityHospital() {
        return hospitalMapper.getMaxPriorityHospital();
    }

    @Override
    public List<Hospital> findHighPriorityHospitals(Integer stick) {
        return hospitalMapper.findHighPriorityHospitals(stick);
    }

    @Override
    public HashMap<String, Object> packHospital(Hospital hospital, HospitalAddress hospitalAddress) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", hospital.getId());
        map.put("name", hospital.getName());
        map.put("nameEn", hospital.getNameEn());
        map.put("image", hospital.getImage());
        map.put("url", hospital.getUrl());
        map.put("stick", hospital.getStick());
        map.put("addressName", hospitalAddress.getAddress_name());
        map.put("addressNameEn", hospitalAddress.getAddress_name_en());
        map.put("addressId", hospitalAddress.getAddress_id());
        return map;
    }

    @Override
    public void cancelStickHospital(Hospital hospital) {
        List<Hospital> lowPriorityHospitals = findHighPriorityHospitals(hospital.getStick());
        if (lowPriorityHospitals.size() != 0) {
            for (Hospital upHospital : lowPriorityHospitals) {
                upHospital.setStick(upHospital.getStick() - 1);
                updateHospital(upHospital);
            }
        }

        hospital.setStick(0);
        updateHospital(hospital);
    }
}
