package com.gyportal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gyportal.model.AjaxResponseBody;
import com.gyportal.model.Enum.Status;
import com.gyportal.model.News;
import com.gyportal.model.PageResult;
import com.gyportal.service.AttachmentService;
import com.gyportal.service.NewsService;
import com.gyportal.utils.RequestUtil;
import com.gyportal.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;

/**
 * create by lihuan at 18/11/1 10:57
 * 新闻
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    public NewsService newsService;

    @Resource
    private AttachmentService attachmentService;

    /**
     * 查询所有新闻
     * @param pageSize
     * @param pageNum
     * @return 分页数据
     */
    @GetMapping("/findAll")
    public AjaxResponseBody findAll(int pageSize, int pageNum,
                                    HttpServletRequest request) {

        PageResult page = newsService.findAll(pageSize, pageNum, "", RequestUtil.getTableName(request));

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(page)
                .buildMsg(RequestUtil.getTableName(request)).build();

    }

    /**
     * 按新闻类型查询
     * @param pageSize
     * @param pageNum
     * @param type 新闻类型
     * @return 分页数据
     */
    @GetMapping("/findAllByType")
    public AjaxResponseBody findAllByType(int pageSize, int pageNum, String type,
                                          HttpServletRequest request) {

        PageResult page = newsService.findAll(pageSize, pageNum, type, RequestUtil.getTableName(request));

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(page).build();
    }

    @GetMapping("/matchNews")
    public AjaxResponseBody matchNews(String words, int pageSize, int pageNum,
                                      @RequestParam(value = "sort", required = false) String sort,
                                      @RequestParam(value = "type", required = false) String type,
                                      HttpServletRequest request) {

        if (StringUtil.isEmpty(sort)) {
            sort = null;
        }
        if (StringUtil.isEmpty(type)) {
            type = null;
        }

        PageResult page = newsService.matchNews(words.trim(), pageSize, pageNum, sort, type, RequestUtil.getTableName(request));

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(page).build();
    }

    /**
     * 查询最新新闻
     * @param count 查询条数
     * @return 新闻List
     */
    @GetMapping("/getLatest")
    public AjaxResponseBody getLatest(int count, HttpServletRequest request) {

        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData(newsService.getLatest(count, "", RequestUtil.getTableName(request))).build();
    }

    /**
     * 按类型查询最新新闻,加锁防止响应数据顺序错乱
     * @param count 查询条数
     * @param type 新闻类型
     * @return 新闻List
     */
    @GetMapping("/getLatestByType")
    public synchronized AjaxResponseBody getLatestByType(int count, String type, HttpServletRequest request) {

        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData(newsService.getLatest(count, type, RequestUtil.getTableName(request))).build();
    }

    /**
     * 浏览量增加1
     * @param id 新闻id
     * @return 无
     */
    @PostMapping("/increasePV")
    public AjaxResponseBody increasePV(int id, HttpServletRequest request) {
        newsService.increasePV(id, RequestUtil.getTableName(request));
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).build();
    }

    /**
     * 获取轮播图
     * @return
     */
    @GetMapping("/getBanners")
    public AjaxResponseBody getBanners(HttpServletRequest request) {

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(newsService.getBanners(RequestUtil.getTableName(request))).build();
    }

    /**
     * 轮播图置顶
     * @param id
     * @param response
     * @return
     */
    @PostMapping("/stickBanner")
    public AjaxResponseBody stickBanner(int id, HttpServletResponse response,
                                        HttpServletRequest request) {
        News news = newsService.findNewsById(id, RequestUtil.getTableName(request));
        if (news == null) {
            response.setStatus(400, "id不存在");
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).build();
        }

        if (news.getStick() == 1) {
            news.setStick(0);
        } else {
            news.setStick(1);
        }

        //不更新时间
        newsService.updateNewsTime(news, RequestUtil.getTableName(request));
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("置顶成功").build();
    }

    /**
     * 图片上传
     * @param image
     * @return 图片url
     */
    @PostMapping("/uploadBanner")
    public AjaxResponseBody uploadBanner(@RequestParam("image") MultipartFile image,
                                          @RequestParam(value = "id", required = false) Integer id,
                                          @RequestParam(value = "path", required = false) String path,
                                          HttpServletResponse response, HttpServletRequest request) {

        if (id == null || id == 0) { //上传普通图片
            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(newsService.savePicture(image, path)).build();
        }

        //上传轮播图
        String pictureUrl = newsService.savePicture(image, path);
        News news = newsService.findNewsById(id, RequestUtil.getTableName(request));
        if (news == null) {
            response.setStatus(400, "id不存在");
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).build();
        }
        news.setUrl(pictureUrl);
        news.setStick(1);
        //不更新时间
        newsService.updateNewsTime(news, RequestUtil.getTableName(request));
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("上传成功").build();
    }

    /**
     * 删除轮播图
     * @param id
     * @param response
     * @return
     */
    @PostMapping("/removeBanner")
    public AjaxResponseBody removeBanner(@RequestParam("id") Integer id,
                                         HttpServletResponse response,
                                         HttpServletRequest request) {
        News news = newsService.findNewsById(id, RequestUtil.getTableName(request));
        if (news == null) {
            response.setStatus(400, "id不存在");
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).build();
        }

        news.setUrl("");
        news.setStick(0);
        newsService.updateNewsTime(news, RequestUtil.getTableName(request));
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("删除成功").build();
    }

    /**
     * base64压缩图片上传
     * @param jsonString
     * @param response
     * @return
     */
    @PostMapping("/uploadImageByBase64")
    public synchronized AjaxResponseBody uploadImageByBase64(@RequestBody String jsonString,
                                                             HttpServletResponse response) {

        JSONObject jsonObject = JSON.parseObject(jsonString);
        String imgSrc = (String) jsonObject.get("imgSrc");
        String pictureUrl = newsService.savePictureByBase64(imgSrc, "/u/cms/www");

        if (StringUtil.isEmpty(pictureUrl)) {
            response.setStatus(400, "图片上传失败");
        }

        Map<String, String> urls = new HashMap<>();
        urls.put("url", pictureUrl);
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(urls).build();

    }

    /**
     *
     * 多线程图片上传
     * @param jsonString(图片数组)
     * @return
     */
    @PostMapping("/asyncUploadImageByBase64")
    public AjaxResponseBody asyncUploadImageByBase64(@RequestBody String jsonString) {

        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONArray jsonArray = (JSONArray) jsonObject.get("images");
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            map.put(i, (String) jsonArray.get(i));
        }

        Map<Integer, String> resultMap = newsService.asyncSavePictureByBase64(map, "/u/cms/www");

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(resultMap).build();

    }

    /**
     * 通过id查询新闻
     * @param id
     * @return news
     */
    @GetMapping("/getNewsById")
    public AjaxResponseBody getNewsById(int id, HttpServletRequest request) {
        News news = newsService.findNewsById(id, RequestUtil.getTableName(request));
        if (news == null) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("新闻id不存在").build();
        }

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(news).build();
    }

    /**
     * 获取热门新闻
     * @return news
     */
    @GetMapping("/getHotNews")
    public AjaxResponseBody getHotNews(HttpServletRequest request) {
        News news = newsService.getHotNews(RequestUtil.getTableName(request));

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(news).build();
    }

    /**
     * 设置热门新闻
     * @return
     */
    @GetMapping("/setHotNews")
    public AjaxResponseBody setHotNews(int id, HttpServletRequest request) {

        News news = newsService.findNewsById(id, RequestUtil.getTableName(request));

        if (news == null) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("新闻id不存在").build();
        }

        newsService.setHotNews(id, RequestUtil.getTableName(request));

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).build();
    }

    /**
     * 查询上一篇和下一篇
     * @param id 当前新闻id
     * @param type 当前新闻类型
     * @return 新闻list(size = 2)
     */
    @GetMapping("/fetchNearNewsById")
    public AjaxResponseBody fetchNearNewsById(int id, String type, HttpServletRequest request) {
        List<News> newsList = newsService.fetchNearNewsById(id, type, RequestUtil.getTableName(request));
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(newsList).build();
    }

    /**
     * 增加新闻
     * @param
     * @return
     */
    @PostMapping("/insertNews")
    public AjaxResponseBody insertNews(@RequestBody String jsonString,
                                       @AuthenticationPrincipal Principal principal,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        News news = new News();
        news.setTitle((String) jsonObject.get("title"));
        news.setType((String) jsonObject.get("type"));

        String content = (String) jsonObject.get("content");
        news.setContent(content);
        String tabloid = (String) jsonObject.get("tabloid");
        if (StringUtils.isBlank(tabloid)) {
            tabloid = newsService.spiritTabloid(content);
        }
        news.setTabloid(tabloid);

        news.setIssuer(principal.getName());

        try {
            newsService.insertNews(news, RequestUtil.getTableName(request));
            Integer newsId = newsService.findLastInsertId(RequestUtil.getTableName(request));

            //捕获异常不更改附件
            //插入新附件
            attachmentService.batInsertAttachment(jsonObject, newsId, RequestUtil.getTableName(request));

            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("插入成功").build();

        } catch (Exception e) {
            response.setStatus(400);
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg(e.getMessage()).build();
        }

    }

    /**
     * 更新
     * @param
     * @return
     */
    @PostMapping("/updateNews")
    public AjaxResponseBody updateNews(@RequestBody String jsonString,
                                       @AuthenticationPrincipal Principal principal,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {

        JSONObject jsonObject = JSON.parseObject(jsonString);
        Integer id = (Integer) jsonObject.get("id");
        News saveNews = newsService.findNewsById(id, RequestUtil.getTableName(request));
        if (saveNews == null ) {
            response.setStatus(400, "id不存在");
        }

        News news = new News();
        news.setId(id);
        news.setPage_view(saveNews.getPage_view());
        news.setPublish_date(saveNews.getPublish_date());
        news.setTitle((String) jsonObject.get("title"));
        news.setType((String) jsonObject.get("type"));

        String content = (String) jsonObject.get("content");
        news.setContent(content);
        String tabloid = (String) jsonObject.get("tabloid");
        if (StringUtils.isBlank(tabloid)) {
            tabloid = newsService.spiritTabloid(content);
        }
        news.setTabloid(tabloid);

        news.setIssuer(principal.getName());
        news.setStick(saveNews.getStick());
        news.setUrl(saveNews.getUrl());

        try {
            newsService.updateNews(news, RequestUtil.getTableName(request));

            //清除原附件
            attachmentService.deleteAttachmentByNewsId(id, RequestUtil.getTableName(request));
            //插入新附件
            attachmentService.batInsertAttachment(jsonObject, id, RequestUtil.getTableName(request));

            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("更新成功").build();
        } catch (Exception e) {
            response.setStatus(400);
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg(e.getMessage()).build();
        }

    }

    /**
     * 删除
     * @param groupId 新闻id集合list
     * @return
     */
    @PostMapping("/deleteNews")
    public AjaxResponseBody deleteNews(@RequestBody String groupId,
                                       @AuthenticationPrincipal Principal principal,
                                       HttpServletRequest request) {
        System.out.println(groupId);

        JSONObject jsonObject = JSON.parseObject(groupId);
        JSONArray jsonArray = (JSONArray) jsonObject.get("groupId");
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            ids.add(jsonArray.get(i) + "");
        }
        newsService.deleteNews(ids, principal.getName(), RequestUtil.getTableName(request));
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).build();
    }

    /**
     * 系统维护用
     * @param id
     * @param response
     * @return
     */
    @PostMapping("/updateNewsTime")
    public AjaxResponseBody updateNewsTime(@RequestParam(value = "updateDate", required = false) String updateDate,
                                           @RequestParam(value = "publishDate", required = false) String publishDate,
                                           @RequestParam("id") Integer id,
                                           HttpServletResponse response,
                                           HttpServletRequest request) {
        News news = newsService.findNewsById(id, RequestUtil.getTableName(request));
        if (news == null) {
            response.setStatus(400, "新闻id不存在");
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).build();
        }

        if (!StringUtil.isEmpty(updateDate)) {
            news.setUpdate_date(updateDate);
        }

        if (!StringUtil.isEmpty(publishDate)) {
            news.setPublish_date(publishDate);
        }

        newsService.updateNewsTime(news, RequestUtil.getTableName(request));
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).build();
    }


}
