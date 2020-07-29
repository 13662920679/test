package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.test.utils.HttpRequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ip")
public class IpController {

    //taobao提供的ip查询接口
    private static final String searchIP = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    @RequestMapping("/ipIndex")
    public String ipIndex(){
        return "ipAddress/index";
    }

    @PostMapping("/ipAddr")
    public String ipAddr(@RequestParam("ip") String ip,Map<String,Object> map){
        String targetUrl = searchIP + ip;
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        JSONObject json = httpRequestUtil.getJSONObjectByGet(targetUrl);
        String jsonStr = json.toJSONString();
        map.put("message",jsonStr);
        return "ipAddress/ipAddress";
    }

}
