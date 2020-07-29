package com.example.test.mbgMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.test.pojo.User;
import com.example.test.pojo.UserExample;
import java.util.List;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    // mbg生成的接口方法名和MyBatis-Plus封装好的方法名一样，返回值类型不同
    // 要么改名、要么和封装的方法保持一致的返回值类型
    // 不过改名的话，mapper.xml的名也得变
//    Integer insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    User login(String username, String userpwd);

    Long reg(User user);

    List<User> allUser();

    Page<User> getUserList();
}