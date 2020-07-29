package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VueController {

    @RequestMapping("/testVue")
    public String testVue(){
        return "vue/testVue";
    }

}
