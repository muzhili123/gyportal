package com.gyportal.service;

import com.gyportal.model.News;
import com.gyportal.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * create by lihuan at 18/11/9 17:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void insertUserTest() {
        User user = new User();
        user.setUsername("shijinming");
        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
        user.setPhone("12345678901");
        userService.insertUser(user);
    }

    @Test
    public void StringTest() {
        String url = "C%E8%AF%AD%E8%A8%80";
        try {
            System.out.println(URLDecoder.decode(url, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void newsTest() {
        System.out.println(News.getNewsEnType("通知公告"));
    }

}
