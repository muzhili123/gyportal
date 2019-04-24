package com.gyportal.repository.mybatis;

import com.gyportal.model.Role;
import com.gyportal.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: lihuan
 * Describe:xml配置User表操作
 */
@Repository
public interface UserRepository {

    /**
     *  通过用户名查找用户
     * @param username 用户名
     * @return 用户
     */
    User findUserByUsername(@Param("username") String username);

    /**
     * 用户id查询角色
     * @param id
     * @return
     */
    List<Role> getRolesByUserId(@Param("id") Integer id);

    /**
     * 最近登录时间
     * @param username
     * @param recentlyLanded
     */
    void updateRecentlyLanded(@Param("username")String username, @Param("recentlyLanded")String recentlyLanded);
}
