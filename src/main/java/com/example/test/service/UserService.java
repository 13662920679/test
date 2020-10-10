package com.example.test.service;

import com.example.test.bean.UserBean;
import com.example.test.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    //登录
    User login(String name, String password);

    //注册
    Long reg(User user);

    User selectUser(User user);

}
