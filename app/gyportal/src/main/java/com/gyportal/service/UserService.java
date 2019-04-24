package com.gyportal.service;

import com.gyportal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * create by lihuan at 18/11/1 10:21
 */
public interface UserService {

    /**
     * 查询所有用户
     */
    Page<User> findAll(int pageSize, int pageNum);

    User findUserById(int id);

    /**
     * 新建用户信息
     * @param user
     */
    @Transactional
    void insertUser(User user);

    /**
     * 更新用户信息
     * @param user
     */
    @Transactional
    void updateUser(User user);

    /**
     * 删除用户信息
     * @param groupId
     */
    @Transactional
    void deleteUsers(List<String> groupId);

    /**
     * 更新最近登录时间
     * @param username 用户名
     */
    void updateRecentlyLanded(String username);

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return true--存在  false--不存在
     */
    boolean usernameIsExit(String username);

    /**
     * 判断手机号是否存在
     * @param phone 手机号
     * @return true--存在  false--不存在
     */
    boolean phoneIsExit(String phone);

    /**
     * 通过手机号判断是否为超级用户
     * @param phone 手机号
     * @return
     */
    boolean isSuperAdmin(String phone);

    User findUserByUsername(String username);
}
