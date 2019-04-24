package com.gyportal.mapper;

import com.gyportal.model.FriendLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by lihuan at 19/1/10 13:33
 */
public interface FriendLinkMapper extends JpaRepository<FriendLink, Integer> {

    @Query(value = "SELECT * FROM `friend_link` WHERE name_en != ''", nativeQuery = true)
    List<FriendLink> findAllByLanguage();
}
