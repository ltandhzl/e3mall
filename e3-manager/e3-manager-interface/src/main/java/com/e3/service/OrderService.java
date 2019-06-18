package com.e3.service;

import com.e3.common.pojo.E3Result;
import com.e3.pojo.OrderInfo;
import com.e3.pojo.TbUser;

public interface OrderService {
    E3Result addOrder(OrderInfo orderInfo, TbUser user);
}
