package com.gyportal.service;

import com.gyportal.model.Picture;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * create by lihuan at 18/11/15 17:20
 */
public interface PictureService {

    @Transactional
    void insertPicture(Picture picture);

    @Transactional
    void updatePicture(Picture picture);

    Page<Picture> findAll(int pageSize, int pageNum);

    Picture findById(Integer id);

    Picture findByPath(String path);

    Page<Picture> findAllByDescription(int pageSize, int pageNum, String description);

    void deletePicture(List<Integer> groupId);
}
