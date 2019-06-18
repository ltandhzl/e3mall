package com.e3.controller;

import com.e3.common.pojo.E3Result;
import com.e3.pojo.OrderInfo;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbUser;
import com.e3.service.CartService;
import com.e3.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order/order-cart")
    public String toCart(HttpServletRequest request, Model model){
        TbUser user = (TbUser) request.getAttribute("user");
        List<TbItem> tbItemList = cartService.getRedisItemList(user);
        model.addAttribute("cartList",tbItemList);
        return "order-cart";
    }

    @RequestMapping(value = "/order/create")
    public String toSuccess(OrderInfo orderInfo,HttpServletRequest request,Model model){

        TbUser user = (TbUser) request.getAttribute("user");
        E3Result e3Result = orderService.addOrder(orderInfo, user);
        OrderInfo info= (OrderInfo) e3Result.getData();
        model.addAttribute("orderId",info.getOrderId());
        model.addAttribute("payment",orderInfo.getPayment());
        return "success";
    }
}
