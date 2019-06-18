package com.e3.controller;

import com.e3.common.pojo.CookieUtils;
import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.JsonUtils;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbUser;
import com.e3.service.CartService;
import com.e3.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private ItemService itemService;

    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/cart/add/{itemId}")
    public String cartAdd(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response){
        TbUser user = (TbUser) request.getAttribute("user");
        //说明已经登陆
        if (user!=null){
             cartService.addCart(itemId, user, num);
            return "cartSuccess";
        }

        //未登录状态
        String json = CookieUtils.getCookieValue(request, "cart", true);
        boolean flag=true;
        //如果有cookie
        if (StringUtils.isNotBlank(json)){
            List<TbItem> tbItemList = JsonUtils.jsonToList(json, TbItem.class);
            for (TbItem tbItem:tbItemList){
                //如果cookie中存在itemId
                if (tbItem.getId().equals(itemId)){
                    tbItem.setNum(tbItem.getNum()+num);
                    CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(tbItemList),COOKIE_CART_EXPIRE,true);
                    flag=false;
                    break;
                }
            }
            //如果cookie中不存在itemId
             if (flag){
                 addItem(request,response,tbItemList,num,itemId);
             }
        }else{
            //如果没有cookie
            List<TbItem> tbItemList=new ArrayList<>();
            addItem(request,response,tbItemList,num,itemId);
        }
        return "cartSuccess";
    }

    public void addItem(HttpServletRequest request,HttpServletResponse response,List<TbItem> tbItemList,Integer num,Long itemId){
        TbItem tbItem = itemService.findTbItemById(itemId);
        tbItem.setNum(num);
        String images = tbItem.getImage();
        if (StringUtils.isNotBlank(images)){
            tbItem.setImage(tbItem.getImage().split(",")[0]);
        }
        tbItemList.add(tbItem);
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(tbItemList),COOKIE_CART_EXPIRE,true);
    }

    @RequestMapping(value = "/cart/cart")
    public String toCart(HttpServletRequest request,HttpServletResponse response){

        TbUser user = (TbUser) request.getAttribute("user");
        String cart = CookieUtils.getCookieValue(request, "cart", true);
        List<TbItem> cookieList = new ArrayList<TbItem>();
        if (StringUtils.isNotBlank(cart)) {
            cookieList =JsonUtils.jsonToList(cart,TbItem.class);
        }
        //是登录状态，从redis和cookie中共同取值
        if(user!=null){
            CookieUtils.deleteCookie(request, response, "cart");
            cartService.getMergeItemList(cookieList,user);
            cookieList= cartService.getRedisItemList(user);
        }
        //不是登陆状态，从cookie中取值
        request.setAttribute("cartList",cookieList);

        return "cart";
    }

    @RequestMapping(value = "/cart/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
        TbUser user = (TbUser) request.getAttribute("user");
        //说明已经登陆
        if (user!=null){
            cartService.deleteItemById(itemId, user);
        }
        String cart = CookieUtils.getCookieValue(request, "cart", true);
        List<TbItem> cookieList = new ArrayList<TbItem>();
        if (StringUtils.isNotBlank(cart)) {
            cookieList =JsonUtils.jsonToList(cart,TbItem.class);
            for (TbItem tbItem:cookieList){
                if (tbItem.getId().equals(itemId)){
                    cookieList.remove(tbItem);
                    break;
                }
            }
            CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cookieList),true);
        }

        return "redirect:/cart/cart.html";
    }

    @RequestMapping(value = "/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateItem(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
        TbUser user = (TbUser) request.getAttribute("user");
        //说明已经登陆
        if (user!=null){
            E3Result e3Result = cartService.updateItemById(itemId, num, user);
            return e3Result;
        }
        String cart = CookieUtils.getCookieValue(request, "cart", true);
        List<TbItem> cookieList = new ArrayList<TbItem>();
        if (StringUtils.isNotBlank(cart)) {
            cookieList =JsonUtils.jsonToList(cart,TbItem.class);
            for (TbItem tbItem:cookieList){
                if (tbItem.getId().equals(itemId)){
                   tbItem.setNum(num);
                   break;
                }
            }
            CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cookieList),true);
        }
        return E3Result.ok();
    }
}
