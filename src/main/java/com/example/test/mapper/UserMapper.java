package com.example.test.mapper;

//import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test.bean.UserBean;
import com.example.test.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

//@Repository
public interface UserMapper extends BaseMapper<UserBean> {

    User login(String username, String userpwd);

    int reg(User user);


}
