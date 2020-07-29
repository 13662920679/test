package com.example.test.serviceImpl;


import com.example.test.mbgMapper.UserMapper;
import com.example.test.pojo.User;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String username, String userpwd) {
        return userMapper.login(username,userpwd);
    }

    @Override
    public Long reg(User user) {
        return userMapper.reg(user);
    }

}
