package com.example.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.test.bean.PersonBean;
import com.example.test.bean.UserBean;
import com.example.test.mbgMapper.UserMapper;
import com.example.test.pojo.User;
import com.example.test.service.UserService;
import com.example.test.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.List;

@SpringBootTest
class TestApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = userService.login("user", "12");
        if (user!=null){
            System.out.println("登录成功!!!用户名:"+user.getUsername());
        }else{
            System.out.println("登录失败");
        }
    }

    @Test
    void testMP(){
        User user = new User();
        user.setUsername("123456");
        user.setUserpwd(DigestUtils.md5DigestAsHex("123456".getBytes()));
        Integer insert = userMapper.insert(user);
        if (insert>0){
            System.out.println("insert成功");
        }else{
            System.out.println("insert失败");
        }
    }

    /**
     * 分页
     */
    @Test
    void queryUserForPage(){
        IPage<User> userIPage = new Page<>(3,2);
        userIPage = userMapper.selectPage(userIPage, null);
        List<User> users = userIPage.getRecords();
        for (User u:users){
            System.out.println(u.getUsername());
        }
    }

}
