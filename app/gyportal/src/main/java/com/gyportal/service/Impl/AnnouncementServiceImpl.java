package com.gyportal.service.Impl;

import com.gyportal.mapper.AnnouncementMapper;
import com.gyportal.model.Announcement;
import com.gyportal.service.AnnouncementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * create by lihuan at 18/11/1 13:04
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> findAll(int pageSize, int page) {
        return announcementMapper.findAll(pageSize, page);
    }

    @Override
    public Announcement getLatest() {
        List<Announcement> list = announcementMapper.getLatest();
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer getAnnouncementCount() {
        return announcementMapper.getAnnouncementCount();
    }
}
