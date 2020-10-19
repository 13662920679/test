package com.example.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;


@SpringBootApplication
@SpringBootConfiguration
//@ComponentScan(value = "com.example.test.config")//加上会403错误
//@ComponentScan(basePackages = {"com.example.test.serviceImpl","com.example.test.service"})//加上会404错误
@MapperScan("com.example.test.mbgMapper")//扫描Mapper接口类，并生成对应的实现类
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
