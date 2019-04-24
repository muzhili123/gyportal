package com.gyportal.repository.mybatis;

import com.gyportal.model.HospitalAddress;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create by lihuan at 19/1/10 11:28
 */
@Repository
public interface HospitalAddressReposity {

    List<HospitalAddress> findAllByPage(@Param("start") int start, @Param("pageSize") int pageSize,
                                        @Param("language") String language);

    Integer count(@Param("language") String language);

    List<HospitalAddress> findAll();

    HospitalAddress getOne(@Param("hospitalAddressId") Integer hospitalAddressId);

    void insert(HospitalAddress hospitalAddress);

    void update(HospitalAddress hospitalAddress);

    void delete(@Param("groupId") List<String> groupId);

    Integer findIdByAddressName(@Param("addressName") String addressName);

    List<HospitalAddress> findHospitalAddressByName(@Param("addressName") String addressName);
}
