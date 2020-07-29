package com.example.test.utils;

import com.example.test.pojo.RoleUser;
import com.example.test.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDatailService implements UserDetailsService {

    @Autowired
    private RoleUserService roleUserService;

    /**
     * 需新建配置类注册一个指定的加密方式Bean，或在下一步Security配置类中注册指定
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RoleUser roleUser = roleUserService.getRoleUserByUsername(username);
        if (roleUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 得到用户角色
        String role = roleUser.getRole();

        // 角色集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
        authorities.add(new SimpleGrantedAuthority("ROLE_"+role));

        // 如果数据库是明文，这里需加密密码
        return new User(roleUser.getUsername(),roleUser.getPassword(),authorities);
    }
}
