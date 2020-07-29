package com.example.test.serviceImpl;

import com.example.test.mbgMapper.RoleUserMapper;
import com.example.test.pojo.RoleUser;
import com.example.test.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RoleUser getRoleUserByUsername(String username) {
        RoleUser roleUser = roleUserMapper.getRoleUserByUsername(username);
        return roleUser;
    }

    @Override
    public int insert(RoleUser roleUser) {
        roleUser.setPassword(passwordEncoder.encode(roleUser.getPassword()));
        int insert = roleUserMapper.insert(roleUser);
        return insert;
    }
}
