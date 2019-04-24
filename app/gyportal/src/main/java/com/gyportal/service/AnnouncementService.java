package com.gyportal.service;

import com.gyportal.model.Announcement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * create by lihuan at 18/11/1 12:55
 */
public interface AnnouncementService {

    List<Announcement> findAll(@Param("pageSize") int pageSize, @Param("start") int start);

    Announcement getLatest();

    Integer getAnnouncementCount();
}
