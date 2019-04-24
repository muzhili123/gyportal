package com.gyportal.controller;

import com.gyportal.model.AjaxResponseBody;
import com.gyportal.model.Enum.Status;
import com.gyportal.model.User;
import com.gyportal.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

/**
 * 用户
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 查询所有用户
     * @param pageSize
     * @param pageNum
     * @return 分页数据
     */
    @GetMapping("/findAll")
    public AjaxResponseBody findAll(int pageSize, int pageNum) {
        Page<User> page = userService.findAll(pageSize, pageNum);

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(page).build();
    }

    @GetMapping("/getCurrentUser")
    public AjaxResponseBody getCurrentUser(@AuthenticationPrincipal Principal principal) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(user).build();
    }

    /**
     * 更新用户最近登录时间
     * @param principal 身份认证
     * @return
     */
    @PostMapping("/updateRecentlyLanded")
    public AjaxResponseBody updateRecentlyLanded(@AuthenticationPrincipal Principal principal) {
        String username = principal.getName();
        userService.updateRecentlyLanded(username);

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).build();
    }

    /**
     * 增加/注册
     * @param user
     * @return status 400-用户名或手机号存在
     */
    @PostMapping("/insertUser")
    public AjaxResponseBody insertUser(User user) {

        if (userService.usernameIsExit(user.getUsername())) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("用户名已存在").build();
        }

        if (userService.phoneIsExit(user.getPhone())) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("手机号已存在").build();
        }

        try {
            userService.insertUser(user);
            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("注册成功").build();
        } catch (Exception e) {
            return new AjaxResponseBody.Builder().buildStatus(Status.ERROR).buildMsg("注册失败").build();
        }

    }

    /**
     * 更新
     * @param user
     * @return status 400-用户名或手机号存在
     */
    @PostMapping("/updateUser")
    public AjaxResponseBody updateUser(User user) {

        //数据库中将有更新的用户
        User updateUser = userService.findUserById(user.getId());

        //用户名已更改，对用户名校验
        if (!user.getUsername().equals(updateUser.getUsername())
                || userService.usernameIsExit(user.getUsername())) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("用户名已存在").build();
        }

        //手机号已更改，对手机号校验
        if (!user.getPhone().equals(updateUser.getPhone())
                || userService.phoneIsExit(user.getPhone())) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("手机号已存在").build();
        }
        try {
            userService.updateUser(user);
            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("更新成功").build();
        } catch (Exception e) {
            return new AjaxResponseBody.Builder().buildStatus(Status.ERROR).buildMsg("更新失败").build();
        }

    }

    /**
     * 删除
     * @param groupId 用户id集合list
     * @return
     */
    @PostMapping("/deleteUser")
    public AjaxResponseBody deleteUser(@RequestBody List<String> groupId ) {
        userService.deleteUsers(groupId);
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).build();
    }

}
