package com.gyportal.service.security;

import com.gyportal.controller.UserController;
import com.gyportal.model.User;
import com.gyportal.repository.mybatis.UserRepository;
import com.gyportal.component.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //构建用户信息的逻辑(取数据库/LDAP等用户信息)
        User user = userRepository.findUserByUsername(username);

        log.info(username + "----------");

        if(user == null){
            return (UserDetails) new UsernameNotFoundException("用户不存在");
        }

        SelfUserDetails userInfo = new SelfUserDetails();
        userInfo.setUsername(username);
        userInfo.setPassword(user.getPassword());

        TimeUtil timeUtil = new TimeUtil();
        String recentlyLanded = timeUtil.getFormatDateForSix();
        userRepository.updateRecentlyLanded(username, recentlyLanded);

//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//
//        for(Role role : user.getRoles()){
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        userInfo.setAuthorities(authorities);

        Set authoritiesSet = new HashSet();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        authoritiesSet.add(authority);
        userInfo.setAuthorities(authoritiesSet);

        log.info(username + "-用户登录成功-" + authority.getAuthority());

        return userInfo;
    }
}
