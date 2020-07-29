package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @RequestMapping("/index")
    public String sayHello(){
        return "index";
    }
    @RequestMapping("/huang")
    public String huang(){
        System.out.println("huang");
        return "huang";
    }
    @RequestMapping("/toPage/{page}")
    public ModelAndView toPage(@PathVariable String page){
        System.out.println("come in toPage");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(page);
        return modelAndView;
    }

    //测试thymeleaf效果
    @RequestMapping("/thymeleaf")
    public ModelAndView thymeleaf(){
        ModelAndView mav=new ModelAndView();
        mav.addObject("message", "SpringBoot大爷你好！");
        mav.setViewName("thymeleafTest");
        return mav;
    }
}
