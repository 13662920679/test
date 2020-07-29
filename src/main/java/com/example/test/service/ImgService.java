package com.example.test.service;

import com.example.test.pojo.Img;
import org.springframework.stereotype.Service;

@Service
public interface ImgService {
    Img selectById(Long id);

    int updataUrlById(Long id,String url);
}
