package com.e3.interceptor;

import com.e3.common.pojo.CookieUtils;
import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.JedisClient;
import com.e3.common.pojo.JsonUtils;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbUser;
import com.e3.service.CartService;
import com.e3.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OrderInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = CookieUtils.getCookieValue(httpServletRequest, "TT_TOKEN");
        if (StringUtils.isBlank(token)){//不存在user的cookie
            httpServletResponse.sendRedirect("http://localhost:8088/page/login?path="+httpServletRequest.getRequestURL());
            return false;
        }
        E3Result e3Result = userService.getUserInfo(token);
        if (e3Result.getStatus()!=200){
            httpServletResponse.sendRedirect("http://localhost:8088/page/login?path="+httpServletRequest.getRequestURL());
            return false;
        }
        TbUser tbUser= (TbUser) e3Result.getData();
        httpServletRequest.setAttribute("user",tbUser);
        String cart = CookieUtils.getCookieValue(httpServletRequest, "cart", true);
        if (StringUtils.isNotBlank(cart)){
            List<TbItem> tbItemList = JsonUtils.jsonToList(cart, TbItem.class);
            cartService.getMergeItemList(tbItemList,tbUser);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
