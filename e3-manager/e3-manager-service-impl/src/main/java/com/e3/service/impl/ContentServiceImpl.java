package com.e3.service.impl;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.EasyUIDataGrid;
import com.e3.common.pojo.JedisClient;
import com.e3.common.pojo.JsonUtils;
import com.e3.mapper.TbContentMapper;
import com.e3.pojo.TbContent;
import com.e3.pojo.TbContentExample;
import com.e3.service.ContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;

    @Value("${REDIS_CONTENT}")
    private String REDIS_CONTENT;

    @Autowired
    private JedisClient jedisClient;
    @Override
    public E3Result addContent(TbContent content) {

        content.setUpdated(new Date());
        content.setCreated(new Date());
        tbContentMapper.insert(content);
        //同步缓存

        try {
            jedisClient.hdel(REDIS_CONTENT,content.getCategoryId().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return E3Result.ok();
    }

    @Override
    public EasyUIDataGrid findContentList(Long categoryId, Integer page, Integer rows) {

        PageHelper.startPage(page,rows);
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = tbContentMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbContent> pageInfo=new PageInfo<TbContent>(tbContents);
        long total = pageInfo.getTotal();
        EasyUIDataGrid grid=new EasyUIDataGrid();
        grid.setTotal(total);
        grid.setRows(tbContents);
        return grid;
    }

    @Override
    public List<TbContent> findContentByCatId(Long categoryId){
        //获取redis缓存中数据
        try {
            String hget = jedisClient.hget(REDIS_CONTENT, categoryId + "");
            if (StringUtils.isNotBlank(hget)){
                List<TbContent> tbContents = JsonUtils.jsonToList(hget, TbContent.class);
                return tbContents;
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = tbContentMapper.selectByExampleWithBLOBs(example);
        try {
            //向redis缓存中添加数据
           jedisClient.hset(REDIS_CONTENT,categoryId+"", JsonUtils.objectToJson(tbContents));
        }catch (Exception e){
           e.printStackTrace();
        }
        return tbContents;
    }
}
