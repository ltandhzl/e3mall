package com.e3.service.impl;

import com.e3.common.pojo.JedisClient;
import com.e3.common.pojo.JsonUtils;
import com.e3.mapper.TbItemDescMapper;
import com.e3.mapper.TbItemMapper;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemDesc;
import com.e3.pojo.TbItemDescExample;
import com.e3.pojo.TbItemExample;
import com.e3.service.ItemDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemDetailServiceImpl implements ItemDetailService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JedisClient jedisClient;
    @Override
    public TbItem findTbItemById(Long id) {
        try {
            String s = jedisClient.get("E3_ITEM:" + id + ":BASE");
            if (StringUtils.isNotBlank(s)){
                TbItem tbItem = JsonUtils.jsonToPojo(s, TbItem.class);
                return tbItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItemExample example=new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        if (tbItems!=null && tbItems.size()>0){
            try {
                jedisClient.set("E3_ITEM:"+id+":BASE", JsonUtils.objectToJson(tbItems.get(0)));
                jedisClient.expire("E3_ITEM:"+id+":BASE",3600);//设置过期时间
            }catch (Exception e){
                e.printStackTrace();
            }
            return tbItems.get(0);
        }
        return null;


    }

    @Override
    public TbItemDesc findTbItenDescById(Long id) {
        try {
            String s = jedisClient.get("E3_ITEM:" + id + ":DESC");
            if (StringUtils.isNotBlank(s)){
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(s, TbItemDesc.class);
                return tbItemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
        if (tbItemDesc!=null){
            try {
                jedisClient.set("E3_ITEM:"+id+":DESC", JsonUtils.objectToJson(tbItemDesc));
                jedisClient.expire("E3_ITEM:"+id+":DESC",3600);
            }catch (Exception e){
                e.printStackTrace();
            }
            return tbItemDesc;
        }
        return null;

    }
}
