package com.gyportal.mapper;

import com.gyportal.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by lihuan at 18/12/20 13:26
 */
public interface VisitorMapper extends JpaRepository<Visitor, Integer> {

    @Query(value = "SELECT SUM(count) FROM visitor", nativeQuery = true)
    int getVisitorCount();

    @Query(value = "select count(*) from visitor where ip = ?1 and to_days(update_time) = to_days(now())", nativeQuery = true)
    Integer findVisitorByDay(String clientIp);

    @Query(value = "SELECT DATE_FORMAT(visitor.update_time,'%Y-%m-%d') days,SUM(count) COUNT FROM visitor GROUP BY days", nativeQuery = true)
    List<List<Object>> findVisitorGroupByDay();

    @Query(value = "SELECT DATE_FORMAT(visitor.update_time,'%Y-%m') months,SUM(count) COUNT FROM visitor GROUP BY months", nativeQuery = true)
    List<List<Object>> findVisitorGroupByMonth();

    @Query(value = "SELECT SUM(count) FROM `visitor` WHERE visitor.update_time LIKE CONCAT(?1,'%')", nativeQuery = true)
    Integer getVisitorCountByDate(String date);
}
