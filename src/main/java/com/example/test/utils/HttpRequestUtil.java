package com.example.test.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpRequestUtil {
    public JSONObject getJSONObjectByGet(String url){
        JSONObject resultJsonObject=null;

        //创建httpClient连接
        CloseableHttpClient httpClient = HttpClients.createDefault();

        StringBuilder urlStringBuilder=new StringBuilder(url);
        StringBuilder entityStringBuilder=new StringBuilder();
        //利用URL生成一个HttpGet请求
        HttpGet httpGet=new HttpGet(urlStringBuilder.toString());
        // HttpClient 发送Post请求
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse=httpClient.execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //得到httpResponse的状态响应码
        if (httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
            //得到httpResponse的实体数据
            HttpEntity httpEntity=httpResponse.getEntity();
            if (httpEntity!=null) {
                BufferedReader reader=null;
                try {
                    reader=new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8*1024);
                    String line=null;
                    while ((line=reader.readLine())!=null) {
                        entityStringBuilder.append(line);
                    }
                    // 从HttpEntity中得到的json String数据转为json
                    String json=entityStringBuilder.toString();
                    resultJsonObject= JSON.parseObject(json);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            //关闭流
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return resultJsonObject;
    }
}
