package com.e3.controller;

import com.e3.common.pojo.CookieUtils;
import com.e3.common.pojo.E3Result;
import com.e3.pojo.TbUser;
import com.e3.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @RequestMapping(value = "/page/login")
    public String showLogin(String path, Model model){
        model.addAttribute("redirect",path);
        return "login";
    }
    @RequestMapping(value = "/page/register")
    public String showRegister(){
        return "register";
    }

    @RequestMapping(value = "/user/check/{param}/{type}")
    @ResponseBody
    public E3Result checkData(@PathVariable String param,@PathVariable Integer type){
        E3Result e3Result = userService.checkData(param, type);
        return e3Result;
    }

    @RequestMapping(value = "/user/register")
    @ResponseBody
    public E3Result register(TbUser user){
        E3Result e3Result = userService.register(user);
        return e3Result;
    }

    @RequestMapping(value = "/user/login")
    @ResponseBody
    public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        E3Result e3Result = userService.login(username, password);
        if (e3Result.getStatus()==200){
            String token =  e3Result.getData().toString();
            CookieUtils.setCookie(request,response,TOKEN_KEY,token);
        }

        return e3Result;
    }
    @RequestMapping(value = "/user/token/{token}")
    @ResponseBody
    public Object showUserInfo(@PathVariable String token, String callback){
        E3Result e3Result = userService.getUserInfo(token);
        if (StringUtils.isNotBlank(callback)){
            //把结果封装成一个js语句响应
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(e3Result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return e3Result;
    }
}
