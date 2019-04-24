package com.gyportal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gyportal.mapper.FriendLinkMapper;
import com.gyportal.model.AjaxResponseBody;
import com.gyportal.model.Enum.Status;
import com.gyportal.model.FriendLink;
import com.gyportal.model.News;
import com.gyportal.model.Picture;
import com.gyportal.properties.MyProperties;
import com.gyportal.service.NewsService;
import com.gyportal.service.PictureService;
import com.gyportal.utils.ImageUtil;
import com.gyportal.utils.RequestUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by lihuan at 18/11/15 17:47
 * 网站静态资源文件管理
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Resource
    private PictureService pictureService;

    @Resource
    private FriendLinkMapper friendLinkMapper;

    @Resource
    private NewsService newsService;

    @Resource
    private MyProperties myProperties;

    //图片资源----------------------------------------

    @GetMapping("/findById")
    public AjaxResponseBody findById(int id) {
        Picture picture = pictureService.findById(id);

        if (picture != null) {
            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(picture).build();
        }

        return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("id不存在").build();
    }

    @GetMapping("/findByPath")
    public AjaxResponseBody findByPath(String path) {
        Picture picture = pictureService.findByPath(path);

        if (picture != null) {
            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(picture).build();
        }

        return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("该路径下没有图片").build();
    }

    @GetMapping("/findAll")
    public AjaxResponseBody findAll(int pageSize, int pageNum) {
        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData(pictureService.findAll(pageSize, pageNum)).build();
    }

    @GetMapping("/findAllByDescription")
    public AjaxResponseBody findAllByDescription(int pageSize, int pageNum, String description) {
        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData(pictureService.findAllByDescription(pageSize, pageNum, description)).build();
    }

    /**
     * 更换图片资源
     * @param picture
     * @param path
     * @param response
     * @return
     */
    @PostMapping("/replacePicture")
    public AjaxResponseBody updatePicture(@RequestParam("picture") MultipartFile picture,
                                          @RequestParam("path") String path,
                                          HttpServletResponse response) {

        try {
            ImageUtil.replaceImg(picture, myProperties.getPathsProperties().getImage() + path);
        } catch (IOException e) {
            response.setStatus(400, "更换失败");
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg(e.getMessage()).build();
        }

        response.setStatus(200, "更换成功");
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("更换成功").build();

    }

    //静态新闻
    @GetMapping("/findStaticArticle")
    public AjaxResponseBody findStaticArticle(HttpServletRequest request) {

        List<Map<String, Object>> newsList = newsService.findStaticArticle(RequestUtil.getTableName(request));

        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData(newsList).build();
    }


    //友情链接------------------------------------------------------------

    /**
     * 友链查询
     * @return
     */
    @GetMapping("/findFriendLink")
    public AjaxResponseBody findFriendLink(HttpServletRequest request) {
        List<FriendLink> friendLinks;

        String language = RequestUtil.getContentLanguage(request);
        if (language.equals("en")) {
            friendLinks = friendLinkMapper.findAllByLanguage();
        } else {
            friendLinks = friendLinkMapper.findAll();
        }

        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData(friendLinks).build();
    }

    @GetMapping("/getFriendLinkById")
    public AjaxResponseBody getFriendLinkById(int id) {
        FriendLink friendLink = friendLinkMapper.getOne(id);

        if (friendLink == null) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL)
                    .buildMsg("id不存在").build();
        } else {
            return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                    .buildData(friendLink).build();
        }

    }

    @PostMapping("/insertFriendLink")
    public AjaxResponseBody insertFriendLink(@RequestBody String jsonString) {

        JSONObject jsonObject = JSON.parseObject(jsonString);
        FriendLink friendLink = new FriendLink();
        friendLink.setName(jsonObject.getString("name"));
        friendLink.setNameEn(jsonObject.getString("nameEn"));
        friendLink.setUrl(jsonObject.getString("url"));
        friendLink.setEffective(1);

        try {
            friendLinkMapper.save(friendLink);
            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("插入成功").build();
        } catch (Exception e) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg(e.getMessage()).build();
        }

    }

    @PostMapping("/updateFriendLink")
    public AjaxResponseBody updateFriendLink(@RequestBody String jsonString, HttpServletResponse response) {

        JSONObject jsonObject = JSON.parseObject(jsonString);
        FriendLink friendLink = new FriendLink();
        friendLink.setId((Integer) jsonObject.get("id"));
        FriendLink saveFriendLink = friendLinkMapper.findOne((Integer) jsonObject.get("id"));
        if (saveFriendLink == null) {
            response.setStatus(400, "id不存在");
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("id不存在").build();
        }
        friendLink.setName(jsonObject.getString("name"));
        friendLink.setNameEn(jsonObject.getString("nameEn"));
        friendLink.setUrl(jsonObject.getString("url"));
        friendLink.setEffective(saveFriendLink.getEffective());

        try {
            friendLinkMapper.save(friendLink);
            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("更新成功").build();
        } catch (Exception e) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg(e.getMessage()).build();
        }

    }

    @PostMapping("/deleteFriendLink")
    public AjaxResponseBody deleteFriendLink(int id) {
        friendLinkMapper.delete(id);
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).build();
    }
}
