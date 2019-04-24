package com.gyportal.mapper;

import com.gyportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * create by lihuan at 18/11/4 18:59
 */
public interface UserMapper extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE username = ?1 LIMIT 1", nativeQuery = true)
    User findUserByName(String username);

    @Query(value = "SELECT * FROM user WHERE phone = ?1 LIMIT 1", nativeQuery = true)
    User findUserByPhone(String phone);
}
