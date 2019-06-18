package com.e3.controller;

import com.e3.common.pojo.FastDFSClient;
import com.e3.common.pojo.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PicUploadController {

    @Value("${UPLOAD_URL}")
    private String UPLOAD_URL;

    @RequestMapping(value = "/pic/upload",produces= MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody//使用该注解，不会将数据发送给视图解释器，而是直接响应给浏览器
    //如果此处响应的是对象，那么会先解析成json对象
    public String upload(MultipartFile uploadFile){
        String originalFilename = uploadFile.getOriginalFilename();
        String extname = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            FastDFSClient fastDFSClient=new FastDFSClient("classpath:conf/client.conf");
            String file = fastDFSClient.uploadFile(uploadFile.getBytes(), extname);
            String url=UPLOAD_URL+file;
            Map map=new HashMap();
            map.put("error",0);
            map.put("url",url);
            //此时响应给google的数据应该是字符串形式的，否则浏览器会不兼容，上传失败
            return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            Map map=new HashMap();
            map.put("error",1);
            map.put("message","上传失败");
            return  JsonUtils.objectToJson(map);
        }

    }
}
