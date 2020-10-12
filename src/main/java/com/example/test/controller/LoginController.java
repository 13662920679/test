package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;

import com.example.test.pojo.User;
import com.example.test.pojo.vo.UserVO;
import com.example.test.service.UserService;
import com.example.test.utils.VerifyUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    //登录成功失败界面2
    @ResponseBody
    @RequestMapping(value = "/loginInto2",method = RequestMethod.POST)
    public ModelAndView loginInto2(@RequestBody UserVO userVO,HttpServletRequest request){
        HttpSession session = request.getSession();
        String salt = (String) session.getAttribute("loginRandomCode");
//        String salt = "20201009163200";
        User user1 = new User();
        user1.setUsername(userVO.getUsername());
        User user2 = userService.selectUser(user1);
        String userpwd = user2.getUserpwd();
        String userpwdMd5 = userpwd+salt;
        String strMd5 = DigestUtils.md5DigestAsHex(userpwdMd5.getBytes());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginIf");
        if (userVO.getUserpwd().equals(strMd5)){
            System.out.println("登录成功!!!用户名:"+userVO.getUsername());
            modelAndView.addObject("message","成功");
            return modelAndView;
        }else{
            System.out.println("登录失败!");
            modelAndView.addObject("message","失败");
            return modelAndView;
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

    @RequestMapping("/loginInto/{ifLoginSuccess}")
    public ModelAndView loginTf(@PathVariable String ifLoginSuccess){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginIf");
        modelAndView.addObject("message",ifLoginSuccess);
        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/createRandomCode")
    public JSONObject createRandomCode(HttpServletRequest request){
        VerifyUtil verifyUtil = new VerifyUtil();
        String salt = verifyUtil.createRandomCode(4);
        HttpSession session = request.getSession();
        session.setAttribute("loginRandomCode",salt);
        session.setMaxInactiveInterval(10);
        JSONObject json = new JSONObject();
        json.put("loginRandomCode",salt);
        return json;
    }
}
