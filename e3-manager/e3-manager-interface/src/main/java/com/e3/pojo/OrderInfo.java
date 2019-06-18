package com.e3.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderInfo extends TbOrder implements Serializable {

    private TbOrderShipping tbOrderShipping;

    private List<TbOrderItem> orderItemList=new ArrayList<>();

    public TbOrderShipping getTbOrderShipping() {
        return tbOrderShipping;
    }

    public void setTbOrderShipping(TbOrderShipping tbOrderShipping) {
        this.tbOrderShipping = tbOrderShipping;
    }

    public List<TbOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<TbOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
