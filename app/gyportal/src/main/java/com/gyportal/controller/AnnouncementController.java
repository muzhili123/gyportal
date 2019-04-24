package com.gyportal.controller;

import com.gyportal.model.PageResult;
import com.gyportal.service.AnnouncementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * create by lihuan at 18/11/1 10:58
 * 公告
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Resource
    private AnnouncementService announcementService;

    @GetMapping("/findAll")
    public PageResult findAll(int pageSize, int page) {
        if (page < 0) {
            page = 0;
        }

        PageResult pageResult = new PageResult();
        pageResult.setContent(announcementService.findAll(pageSize, page));
        pageResult.setTotalElements(announcementService.getAnnouncementCount());

        return pageResult;
    }
}
