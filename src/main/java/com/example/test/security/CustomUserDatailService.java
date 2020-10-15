package com.example.test.security;

import com.example.test.pojo.RoleUser;
import com.example.test.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDatailService implements UserDetailsService {
    @Autowired
    private RoleUserService roleUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RoleUser roleUser = roleUserService.getRoleUserByUsername(username);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_"+roleUser.getRole());//可多个Role
        /* 注意要添加"ROLE_"作为前缀
        *   因为数据库存储的是没有前缀的
        * 角色必须以`ROLE_`开头，数据库中没有，则在这里加；有则不用加
        *  */
        return new User(username,roleUser.getPassword(),authorityList);
    }
}
