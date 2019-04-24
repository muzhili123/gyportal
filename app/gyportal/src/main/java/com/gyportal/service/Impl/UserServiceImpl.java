package com.gyportal.service.Impl;

import com.gyportal.mapper.UserMapper;
import com.gyportal.model.User;
import com.gyportal.service.UserService;
import com.gyportal.component.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * create by lihuan at 18/11/1 10:48
 */
@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    private TimeUtil timeUtil = new TimeUtil();

    @Override
    public Page<User> findAll(int pageSize, int pageNum) {

        Pageable pageable = new PageRequest(pageNum - 1, pageSize);
        return userMapper.findAll(pageable);
    }

    @Override
    public User findUserById(int id) {
        return userMapper.getOne(id);
    }

    @Override
    public void insertUser(User user) {
        userMapper.save(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.save(user);
    }

    @Override
    public void deleteUsers(List<String> groupId) {
        List<User> users = new ArrayList<>();
        User user = new User();
        for (String str : groupId) {
            user.setId(Integer.valueOf(str));
            users.add(user);
        }
        userMapper.delete(users);
    }

    @Override
    public void updateRecentlyLanded(String username) {
        User user = userMapper.findUserByName(username);
        user.setRecently_landed(timeUtil.getFormatDateForSix());
        userMapper.save(user);
    }


    @Override
    public boolean usernameIsExit(String username) {
        User user = userMapper.findUserByName(username);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean phoneIsExit(String phone) {
        User user = userMapper.findUserByPhone(phone);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isSuperAdmin(String phone) {
        return false;
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByName(username);
    }
}
