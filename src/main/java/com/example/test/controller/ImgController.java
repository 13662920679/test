package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.test.pojo.Img;
import com.example.test.service.ImgService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private ImgService imgService;

    //img首页
    @RequestMapping("/img")
    public String img(){
        return "imgHtml/img";
    }

    //回显图片（仅进页面时执行一次）
    @ResponseBody
    @RequestMapping(value = "/showImg",method = RequestMethod.POST)
    public Object showImg(Long id){
        Img img = imgService.selectById(id);
        String imgUrl = img.getImgUrl();
        JSONObject json = new JSONObject();
        json.put("imgUrl",imgUrl);
        return json.toJSONString();
    }

    //保存新图片
    @ResponseBody
    @RequestMapping(value = "/saveImg",method = RequestMethod.POST)
    public Object saveImg(MultipartFile img){
        JSONObject json = new JSONObject();
        if (img == null) {
            json.put("type", "error");
            json.put("msg", "选择要上传的文件！");
            return json.toJSONString();
        }
        if (img.getSize() > 1024 * 1024 * 10) {
            json.put("type", "error");
            json.put("msg", "文件大小不能超过10M！");
            return json.toJSONString();
        }
        String suffix  = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf(".") + 1);
        System.out.println(suffix);
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            json.put("type", "error");
            json.put("msg", "请选择jpg,jpeg,gif,png格式的图片！");
            return json.toJSONString();
        }
        String savePath = "E:/img/";
        File savePathFile = new File(savePath);
        if(!savePathFile.exists()){
            savePathFile.mkdir();
        }
        String filename = new Date().getTime() + "." + suffix;
        try {
            img.transferTo(new File(savePath+filename));
        } catch (IOException e) {
            json.put("type", "error");
            json.put("msg", "保存文件异常！");
            e.printStackTrace();
            return json.toJSONString();
        }

        int i = imgService.updataUrlById(new Long(1), filename);
        if(i>0){
            json.put("type", "success");
            json.put("msg", "上传图片成功！");
        } else{
            json.put("type", "error");
            json.put("msg", "数据库存储异常！");
        }

        return json.toJSONString();
    }

}
