package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;

import com.example.test.pojo.User;
import com.example.test.pojo.vo.UserVO;
import com.example.test.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 *      @Controller 返回页面
 * */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    //登录界面
    @RequestMapping("/userLogin")
    public String userLogin(){ return "login";
    }

    //登录成功失败界面
    @RequestMapping(value = "/loginInto",method = RequestMethod.POST)
    public ModelAndView loginInto(UserVO userVO){
        String password = DigestUtils.md5DigestAsHex(userVO.getUserpwd().getBytes());
        User user = userService.login(userVO.getUsername(), password);
        String name = HtmlUtils.htmlEscape(user.getUsername());
        System.out.println(name);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginIf");
        if (user!=null){
            System.out.println("登录成功!!!用户名:"+user.getUsername());
            modelAndView.addObject("message","成功");
            return modelAndView;
//            return "success";
        }else{
            System.out.println("登录失败!");
            modelAndView.addObject("message","失败");
            return modelAndView;
//            return "error";
        }
    }

    //  注册
    //      前端用ajax传json参数-->后端controller用实体接收
    @ResponseBody
    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    public Object regAjax(@RequestBody UserVO userVO){
        System.out.println("进来了");
        String password = DigestUtils.md5DigestAsHex(userVO.getUserpwd().getBytes());
        userVO.setUserpwd(password);
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        Long reg = userService.reg(user);//插入user并返回影响行数
        JSONObject json = new JSONObject();
        if (reg>0){
            System.out.println("注册成功!!!影响:"+reg);
            json.put("ifSuccess",true);
            return json.toJSONString();
        }else{
            System.out.println("注册失败!");
            json.put("ifSuccess",false);
            return json.toJSONString();
        }
    }

    @RequestMapping("/onclick")
    public String onclick(){
        return "huang";
    }


}
