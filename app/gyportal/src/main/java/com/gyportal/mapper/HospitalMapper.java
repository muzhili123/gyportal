package com.gyportal.mapper;

import com.gyportal.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by lihuan at 18/11/4 10:07
 */
public interface HospitalMapper extends JpaRepository<Hospital, Integer> {

    @Query(value = "SELECT * FROM hospital where address_id = ?1", nativeQuery = true)
    List<Hospital> findAllByAddressId(Integer id);

    @Query(value = "SELECT * FROM hospital where address_id = ?1 and name_en != '' ", nativeQuery = true)
    List<Hospital> findAllByAddressIdAndLanguage(Integer address_id);



    @Query(value = "SELECT * FROM hospital WHERE stick != 0 order by stick limit ?1", nativeQuery = true)
    List<Hospital> getStickHospitals(int count);

    @Query(value = "SELECT * FROM hospital WHERE stick != 0 and name_en != '' order by stick limit ?1", nativeQuery = true)
    List<Hospital> getStickHospitalsByLanguage(int count);




    @Query(value = "SELECT count(*) FROM hospital WHERE stick != 0", nativeQuery = true)
    Integer getStickHospitalsCount();

    @Query(value = "SELECT hospital.*,hospital_address.address_name as addressName FROM `hospital` " +
            "LEFT JOIN hospital_address on hospital_address.address_id = hospital.address_id WHERE hospital.id = 1", nativeQuery = true)
    Object findHospitalById(Integer id);

    @Query(value = "SELECT * FROM hospital WHERE stick = ?1 limit 1", nativeQuery = true)
    Hospital findHospitalByStick(int stick);

    @Query(value = "SELECT MAX(stick) FROM hospital WHERE stick != 0", nativeQuery = true)
    Integer getMaxPriorityHospital();

    @Query(value = "SELECT * FROM hospital WHERE stick != 0 AND hospital.stick > ?1 ORDER by stick", nativeQuery = true)
    List<Hospital> findHighPriorityHospitals(Integer stick);


}
