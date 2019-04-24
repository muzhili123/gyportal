package com.gyportal.mapper;

import com.gyportal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 弃用
 * create by lihuan at 18/11/4 20:13
 */
public interface NewsMapper{

//    @Query(value = "(SELECT id,title FROM news WHERE id < ?1 AND type = ?2 ORDER by publish_date LIMIT 1) " +
//                   " UNION " +
//                   "(SELECT id,title FROM news WHERE id > ?1 AND type = ?2 ORDER by publish_date LIMIT 1)", nativeQuery = true)
//    List<News> fetchNearNewsById(Integer id, String type);
//
//    @Query(value = "select * from news order by publish_date desc limit ?1", nativeQuery = true)
//    List<News> getLatest(int count);
//
//    @Query(value = "select * from news where type = ?2 order by publish_date desc limit ?1", nativeQuery = true)
//    List<News> getLatestByType(int count, String type);
//
//    @Query(value = "select * from news where type = ?2 limit ?1", nativeQuery = true)
//    List<News> getImageNews(int count, String type);
//
//    @Query(value = "(SELECT * FROM news WHERE id < ?1 AND type = ?2 ORDER by publish_date LIMIT 1)", nativeQuery = true)
//    News fetchFrontNewsById(Integer id, String type);
//
//    @Query(value = "(SELECT * FROM news WHERE id > ?1 AND type = ?2 ORDER by publish_date DESC LIMIT 1)", nativeQuery = true)
//    News fetchBehindNewsBYId(Integer id, String type);
//
//    @Query(value = "SELECT count(*) FROM news WHERE type = ?1", nativeQuery = true)
//    long getCountByType(String type);
//
//    @Query(value = "SELECT * FROM news ORDER BY news.page_view DESC LIMIT 1", nativeQuery = true)
//    News getHotNews();
}
