package com.gyportal.service;

import com.gyportal.model.News;
import com.gyportal.model.PageResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * create by lihuan at 18/11/1 10:59
 */
public interface NewsService {

    public News findNewsById(Integer id, String tableName);

    public Integer findLastInsertId(String tableName);

    public List<News> fetchNearNewsById(Integer id, String type, String tableName);

    public PageResult findAll(int pageSize, int pageNum, String type, String tableName);

    public List<Map<String, Object>> getLatest(int count, String type, String tableName);

    @Transactional
    public void insertNews(News news, String tableName);

    @Transactional
    public void insertNewsList(List<News> newsList, String tableName);

    @Transactional
    public void updateNews(News news, String tableName);

    public void deleteNews(List<String> groupId, String username, String tableName);

    public Integer getNewsCount(String tableName);

    public Integer getNewsCountByType(String type, String tableName);

    public String savePicture(MultipartFile image, String dir);

    public String savePictureByBase64(String imgStr, String dir);

    public Map<Integer, String> asyncSavePictureByBase64(Map<Integer, String> map, String dir);

    public void increasePV(int id, String tableName);

    public News getHotNews(String tableName);

    @Transactional
    public void setHotNews(int id, String tableName);

    public List<News> getAllNews(String tableName);

    public void updateNewsTime(News news, String tableName);

    public List<Map<String, Object>> getBanners(String tableName);

    public PageResult matchNews(String words, int pageSize, int pageNum, String sort, String type, String tableName);

    public List<Map<String, Object>> findStaticArticle(String tableName);

    public String spiritTabloid(String content);

}
