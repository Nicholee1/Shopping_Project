package org.learning.shopping.service;

import org.learning.shopping.model.Role;
import org.learning.shopping.model.UserInfo;
import org.learning.shopping.repository.UserInfoRepository;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserInfoRepository userService;

    //重写认证的过程，由AuthenticationManager去调，从数据库中查找用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户是否存在
        UserInfo user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("username: " + username + " can not be found");
        }
        // 设置用户权限，可在数据库建表，通过用户查询到相应权限(角色)，按以下方式加入
        List<GrantedAuthority> authorities = new ArrayList<>();

        List<Role> list = user.getRoles();
        for (int i = 0; i < list.size(); i++) {
            authorities.add(new SimpleGrantedAuthority(list.get(i).getName()));
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }


}
