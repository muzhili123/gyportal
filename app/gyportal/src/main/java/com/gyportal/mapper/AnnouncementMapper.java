package com.gyportal.mapper;

import com.gyportal.model.Announcement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create by lihuan at 18/11/1 13:05
 */
@Mapper
@Repository
public interface AnnouncementMapper {

    @Select("select * from announcement limit #{start},#{pageSize}")
    public List<Announcement> findAll(@Param("pageSize") int pageSize, @Param("start") int start);

    @Select("select * from announcement order by publish_date desc limit 0,1")
    public List<Announcement> getLatest();

    @Select("select count(*) from announcement")
    public Integer getAnnouncementCount();
}
