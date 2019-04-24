package com.gyportal.mapper;

import com.gyportal.model.SensitiveWord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * create by lihuan at 18/11/6 11:25
 */
public interface SensitiveWordMapper extends JpaRepository<SensitiveWord, Integer> {

}
