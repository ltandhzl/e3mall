package com.e3.service.impl;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.JedisClient;
import com.e3.mapper.TbOrderItemMapper;
import com.e3.mapper.TbOrderMapper;
import com.e3.mapper.TbOrderShippingMapper;
import com.e3.pojo.*;
import com.e3.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JedisClient jedisClient;
    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_GEN_START}")
    private String ORDER_ID_GEN_START;

    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Override
    public E3Result addOrder(OrderInfo orderInfo, TbUser user) {
        Boolean exists = jedisClient.exists(ORDER_ID_GEN_KEY);
        if (!exists){
            jedisClient.set(ORDER_ID_GEN_KEY,ORDER_ID_GEN_START);
        }
        String orderId=jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        orderInfo.setOrderId(orderId);
        orderInfo.setStatus(1);//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderInfo.setUserId(user.getId());
        tbOrderMapper.insert(orderInfo);
        List<TbOrderItem> orderItemList = orderInfo.getOrderItemList();
        for (TbOrderItem tbOrderItem:orderItemList){
            tbOrderItem.setId(jedisClient.incr(ORDER_ID_GEN_KEY).toString());
            tbOrderItem.setOrderId(orderId);
            tbOrderItemMapper.insert(tbOrderItem);
        }
        TbOrderShipping tbOrderShipping = orderInfo.getTbOrderShipping();
        tbOrderShipping.setOrderId(orderId);
        tbOrderShipping.setCreated(new Date());
        tbOrderShipping.setUpdated(new Date());
        tbOrderShippingMapper.insert(tbOrderShipping);
        return E3Result.ok(orderInfo);
    }
}
