package com.example.test.serviceImpl;

import com.example.test.mbgMapper.ImgMapper;
import com.example.test.pojo.Img;
import com.example.test.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;

@Service
public class ImgServiceImpl implements ImgService {

    @Autowired
    private ImgMapper imgMapper;

    @Override
    public Img selectById(Long id) {
        Img img = imgMapper.selectById(id);
        return img;
    }

    @Override
    public int updataUrlById(Long id, String url) {
        int i = imgMapper.updataUrlById(id, url);
        return i;
    }

}
