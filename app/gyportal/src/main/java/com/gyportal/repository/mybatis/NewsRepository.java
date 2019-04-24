package com.gyportal.repository.mybatis;

import com.gyportal.model.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * create by lihuan at 18/11/9 11:10
 */
@Repository
public interface NewsRepository {

    public List<Map<String, Object>> findAll(@Param("pageSize") int pageSize, @Param("start") int start, @Param("tableName") String tableName);

    public List<Map<String, Object>> getLatest(@Param("count") int count, @Param("tableName") String tableName);

    public List<Map<String, Object>> getLatestByType(@Param("count") int count, @Param("type") String type, @Param("tableName") String tableName);

    public List<Map<String, Object>> getImageNews(@Param("count") int count, @Param("type") String type, @Param("tableName") String tableName);

    public Integer getNewsCount(@Param("tableName") String tableName);

    public Integer getCountByType(@Param("type") String type, @Param("tableName") String tableName);

    public News findNewsById(@Param("id") int id, @Param("tableName") String tableName);

    public News fetchFrontNewsById(@Param("id") Integer id, @Param("type") String type, @Param("tableName") String tableName);

    public News fetchBehindNewsBYId(@Param("id") Integer id, @Param("type") String type, @Param("tableName") String tableName);

    public void insertNews(@Param("tableName") String tableName, @Param("news") News news);

    public void updateNews(@Param("tableName") String tableName, @Param("news")News news);

    public void deleteNews(@Param("groupId") List<String> groupId, @Param("issuer") String issuer, @Param("update_date") String update_date, @Param("tableName") String tableName);

    public News getHotNews(@Param("tableName") String tableName);

    public List<Map<String, Object>> findAllByType(@Param("pageSize") int pageSize, @Param("start") int start,
                                                   @Param("type") String type, @Param("tableName") String tableName);

    public List<News> getAllNews(@Param("tableName") String tableName);

    public List<Map<String, Object>> getBanners(@Param("tableName") String tableName);

    Integer findLastInsertId(@Param("tableName") String tableName);

    List<News> matchNews(@Param("keyWords") String keyWords, @Param("pageSize") int pageSize,
                         @Param("start") int start, @Param("sort") String sort, @Param("type") String type, @Param("tableName") String tableName);

    int getMatchNewsCount(@Param("keyWords") String keyWords, @Param("type") String type, @Param("tableName") String tableName);

    void clearHotNews(@Param("tableName") String tableName);

    void setHotNews(@Param("id") int id, @Param("tableName") String tableName);

    List<News> newsLike(@Param("keyWords") String keyWords, @Param("pageSize") int pageSize,
                        @Param("start") int start, @Param("sort") String sort, @Param("type") String type, @Param("tableName") String tableName);

    int getNewsLikeCount(@Param("keyWords") String words, @Param("type") String type, @Param("tableName") String tableName);

    List<Map<String, Object>> findStaticArticle(@Param("tableName") String tableName);
}
