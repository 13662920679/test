package com.example.test.mbgMapper;

import com.example.test.pojo.Img;
import com.example.test.pojo.ImgExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ImgMapper {
    long countByExample(ImgExample example);

    int deleteByExample(ImgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Img record);

    int insertSelective(Img record);

    List<Img> selectByExample(ImgExample example);

    Img selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Img record, @Param("example") ImgExample example);

    int updateByExample(@Param("record") Img record, @Param("example") ImgExample example);

    int updateByPrimaryKeySelective(Img record);

    int updateByPrimaryKey(Img record);


    Img selectById(Long id);

    int updataUrlById(Long id,String url);
}