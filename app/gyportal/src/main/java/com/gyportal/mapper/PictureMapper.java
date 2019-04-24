package com.gyportal.mapper;

import com.gyportal.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * create by lihuan at 18/11/15 17:19
 */
public interface PictureMapper extends JpaRepository<Picture, Integer>, JpaSpecificationExecutor {

    @Query(value = "SELECT * FROM picture WHERE path = ?1 LIMIT 1", nativeQuery = true)
    Picture findByPath(String path);
}
