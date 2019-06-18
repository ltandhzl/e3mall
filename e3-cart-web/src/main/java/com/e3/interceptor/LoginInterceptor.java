package com.e3.interceptor;

import com.e3.common.pojo.CookieUtils;
import com.e3.common.pojo.E3Result;
import com.e3.pojo.TbUser;
import com.e3.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor  implements HandlerInterceptor {


    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String json = CookieUtils.getCookieValue(httpServletRequest, "TT_TOKEN");
        if (StringUtils.isBlank(json)){
            return true;
        }
        E3Result e3Result = userService.getUserInfo(json);
        if (e3Result.getStatus()!=200){
            return true;
        }
        TbUser tbUser = (TbUser) e3Result.getData();
        httpServletRequest.setAttribute("user",tbUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
