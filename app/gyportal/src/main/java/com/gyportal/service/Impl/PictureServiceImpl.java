package com.gyportal.service.Impl;

import com.gyportal.mapper.PictureMapper;
import com.gyportal.model.Picture;
import com.gyportal.service.PictureService;
import com.gyportal.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * create by lihuan at 18/11/15 17:25
 */
@Service(value = "pictureServiceImpl")
public class PictureServiceImpl implements PictureService {

    @Resource
    private PictureMapper pictureMapper;

    @Override
    public void insertPicture(Picture picture) {
        pictureMapper.save(picture);
    }

    @Override
    public void updatePicture(Picture picture) {
        pictureMapper.save(picture);

    }

    @Override
    public Page<Picture> findAll(int pageSize, int pageNum) {
        Pageable pageable = new PageRequest(pageNum - 1, pageSize);

        return pictureMapper.findAll(pageable);
    }

    @Override
    public Picture findById(Integer id) {
        return pictureMapper.findOne(id);
    }

    @Override
    public Picture findByPath(String path) {
        return pictureMapper.findByPath(path);
    }

    @Override
    public Page<Picture> findAllByDescription(int pageSize, int pageNum, String description) {
        Pageable pageable = new PageRequest(pageNum - 1, pageSize);

        Specification<Picture> specification = (root, query, cb) -> {
            Predicate predicate=cb.conjunction();
            if(!StringUtil.isEmpty(description)){
                predicate.getExpressions().add(cb.like(root.get("description"), "%"+ description.trim() +"%"));
            }
            return predicate;
        };

        return pictureMapper.findAll(specification, pageable);
    }

    @Override
    public void deletePicture(List<Integer> groupId) {

        for(Integer id : groupId) {
            pictureMapper.delete(id);
        }

    }
}
