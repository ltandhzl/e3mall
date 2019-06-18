package com.e3.service.impl;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.JedisClient;
import com.e3.common.pojo.JsonUtils;
import com.e3.mapper.TbItemMapper;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbUser;
import com.e3.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_CART}")
    private String REDIS_CART;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public E3Result addCart(Long itemId, TbUser user, Integer num) {

        Boolean hexists = jedisClient.hexists(REDIS_CART + ":" + user.getId(), itemId + "");
        //redis中有该商品的信息
        if (hexists){
            String hget = jedisClient.hget(REDIS_CART + ":" + user.getId(), itemId + "");
            TbItem tbItem = JsonUtils.jsonToPojo(hget, TbItem.class);
            tbItem.setNum(tbItem.getNum()+num);
            jedisClient.hset(REDIS_CART + ":" + user.getId(), itemId + "",JsonUtils.objectToJson(tbItem));
            return E3Result.ok();
        }
        //redis中没有该商品的信息
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        tbItem.setNum(tbItem.getNum()+num);
        String images = tbItem.getImage();
        if (StringUtils.isNotBlank(images)){
            tbItem.setImage(images.split(",")[0]);
        }
        jedisClient.hset(REDIS_CART + ":" + user.getId(), itemId + "",JsonUtils.objectToJson(tbItem));

        return E3Result.ok();
    }

    @Override
    public E3Result getMergeItemList(List<TbItem> cookieList,TbUser user) {

        for (TbItem cookieItem:cookieList){
            addCart(cookieItem.getId(),user,cookieItem.getNum());
        }
        return E3Result.ok();
    }

    @Override
    public E3Result deleteItemById(Long itemId,TbUser user) {
        Boolean hexists = jedisClient.hexists(REDIS_CART + ":" + user.getId(), itemId + "");
        //redis中有该商品的信息
        if (hexists){
            jedisClient.hdel(REDIS_CART + ":" + user.getId(),itemId+"");
        }
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getRedisItemList(TbUser user) {
        List<String> hvals = jedisClient.hvals(REDIS_CART + ":" + user.getId());
        List<TbItem> tbItemList=new ArrayList<>();
        for (String str:hvals) {
            TbItem tbItem = JsonUtils.jsonToPojo(str, TbItem.class);
            tbItemList.add(tbItem);
        }
        return tbItemList;
    }

    @Override
    public E3Result updateItemById(Long itemId, Integer num, TbUser user) {
        Boolean hexists = jedisClient.hexists(REDIS_CART + ":" + user.getId(), itemId + "");
        //redis中有该商品的信息
        if (hexists){
            String hget = jedisClient.hget(REDIS_CART + ":" + user.getId(), itemId + "");
            TbItem tbItem = JsonUtils.jsonToPojo(hget, TbItem.class);
            tbItem.setNum(num);
            jedisClient.hset(REDIS_CART + ":" + user.getId(), itemId + "",JsonUtils.objectToJson(tbItem));
        }
        return E3Result.ok();
    }
}
