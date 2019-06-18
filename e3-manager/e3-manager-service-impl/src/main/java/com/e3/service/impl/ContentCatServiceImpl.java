package com.e3.service.impl;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.EasyUITree;
import com.e3.mapper.TbContentCategoryMapper;
import com.e3.pojo.TbContentCategory;
import com.e3.pojo.TbContentCategoryExample;
import com.e3.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCatServiceImpl implements ContentCatService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<EasyUITree> findContentCatList(Long parentId) {

        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(example);
        List<EasyUITree> list=new ArrayList<EasyUITree>();
        for (TbContentCategory contentCategory:tbContentCategories){
            EasyUITree tree=new EasyUITree();
            tree.setId(contentCategory.getId());
            tree.setText(contentCategory.getName());
            tree.setState(contentCategory.getIsParent()?"closed":"open");
            list.add(tree);
        }

        return list;
    }

    @Override
    public E3Result addContentCat(Long parentId, String name) {
        TbContentCategory category=new TbContentCategory();
        category.setIsParent(false);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        category.setName(name);
        category.setParentId(parentId);
        category.setStatus(1);
        category.setSortOrder(2);
        tbContentCategoryMapper.insert(category);
        E3Result result=new E3Result(category);
        return result;
    }

    @Override
    public E3Result updateContentCat(TbContentCategory category) {
        tbContentCategoryMapper.updateByPrimaryKeySelective(category);
        return E3Result.ok();
    }
}
