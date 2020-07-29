package com.example.test.controller;

import com.example.test.pojo.RoleUser;
import com.example.test.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityController {
    @Autowired
    private RoleUserService roleUserService;

    @GetMapping("/admin/greeting")
    public String greeting1() {
        return "Hello,World!";
    }

    @RequestMapping("/admin/login")
    public String login1() {
        return "huang";
    }

    @GetMapping("/employee/greeting")
    public String greeting2() {
        return "Hello,World!";
    }

    @GetMapping("/employee/login")
    @PreAuthorize("hasRole('TEST')")
    public String login2() {
        return "huang";
    }

    @RequestMapping("/test")
    @PreAuthorize("hasRole('ROLE_TEST')")
    public String oauth(){
        return "test";
    }

    /**
     * 新增权限角色
     * 单表
     * @param roleUser
     * @return
     */
    @RequestMapping("/addRole")
    public String addRole(@RequestBody RoleUser roleUser){
        int insert = roleUserService.insert(roleUser);
        if (insert>0){
            return "../static/state/roleUserCreateSuccess.html";
        }else{
            return "../static/state/roleUserCreateError.html";
        }
    }

}
