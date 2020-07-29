package com.example.test.service;

import com.example.test.pojo.RoleUser;
import org.springframework.stereotype.Service;

@Service
public interface RoleUserService {
    RoleUser getRoleUserByUsername(String username);

    int insert(RoleUser roleUser);
}
