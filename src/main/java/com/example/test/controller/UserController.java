package com.example.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.test.mbgMapper.UserMapper;
import com.example.test.pojo.User;
import com.example.test.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    private List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public ResponseEntity<List<User>> selectById(){
//        users = userMapper.allUser();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("username");
        users = userMapper.selectList(queryWrapper);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/queryUserForPage")
    public ResponseEntity<List<User>> queryUserForPage(Integer current,Integer size){
        IPage<User> userIPage = new Page<>(current,size);//当前页、每页大小
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username","u");
        userIPage = userMapper.selectPage(userIPage, queryWrapper);
        users = userIPage.getRecords();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/getUserList")
    public ResponseEntity<List<User>> getUserList(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);//当前页、每页大小
        com.github.pagehelper.Page<User> userList = userMapper.getUserList();
        return ResponseEntity.ok(userList);
    }


}
