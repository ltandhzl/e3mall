package com.e3.service;

import com.e3.common.pojo.E3Result;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbUser;

import java.util.List;


public interface CartService {

    E3Result addCart(Long itemId, TbUser user,Integer num);

    E3Result getMergeItemList(List<TbItem> cookieList, TbUser user);

    E3Result deleteItemById(Long itemId,TbUser user);

    E3Result updateItemById(Long itemId, Integer num, TbUser user);

    List<TbItem> getRedisItemList(TbUser user);
}
