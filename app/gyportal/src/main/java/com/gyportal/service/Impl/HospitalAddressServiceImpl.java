package com.gyportal.service.Impl;

import com.gyportal.model.HospitalAddress;
import com.gyportal.repository.mybatis.HospitalAddressReposity;
import com.gyportal.service.HospitalAddressService;
import com.gyportal.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * create by lihuan at 19/1/9 12:13
 */
@Service(value = "hospitalAddressServiceImpl")
public class HospitalAddressServiceImpl implements HospitalAddressService {

    @Resource
    private HospitalAddressReposity hospitalAddressReposity;

    @Override
    public List<HospitalAddress> findHospitalAddressByName(String addressName) {

        if (StringUtil.isEmpty(addressName)) {
            addressName = "";
        }

        addressName = new StringBuilder("%").append(addressName.trim()).append("%").toString();

        return hospitalAddressReposity.findHospitalAddressByName(addressName);
    }

    @Override
    public HospitalAddress getHospitalAddressById(Integer hospitalAddressId) {
        return hospitalAddressReposity.getOne(hospitalAddressId);
    }

    @Override
    public void insertHospitalAddress(HospitalAddress hospitalAddress) {
        hospitalAddressReposity.insert(hospitalAddress);

    }

    @Override
    public void updateHospitalAddress(HospitalAddress hospitalAddress) {
        hospitalAddressReposity.update(hospitalAddress);
    }

    @Override
    public void deleteHospitalAddress(List<String> groupId) {

        hospitalAddressReposity.delete(groupId);

    }

    @Override
    public Integer findByAddressName(String addressName, String addressNameEn) {
        Integer addressId = hospitalAddressReposity.findIdByAddressName(addressName);

        if (addressId != null) {
            HospitalAddress hospitalAddress = getHospitalAddressById(addressId);
            if (!addressNameEn.equals(hospitalAddress.getAddress_name_en())) {
                hospitalAddress.setAddress_name_en(addressNameEn);
                hospitalAddressReposity.update(hospitalAddress);
            }

            return addressId;
        }

        HospitalAddress hospitalAddress = new HospitalAddress();
        hospitalAddress.setAddress_name(addressName);
        hospitalAddress.setAddress_name_en(addressNameEn);
        hospitalAddress.setParent_id(0);
        insertHospitalAddress(hospitalAddress);

        //获取新id
        addressId = hospitalAddressReposity.findIdByAddressName(addressName);

        return addressId;
    }
}
