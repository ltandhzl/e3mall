package com.e3.service.impl;

import com.e3.common.pojo.EasyUITree;
import com.e3.mapper.TbItemCatMapper;
import com.e3.pojo.TbItemCat;
import com.e3.pojo.TbItemCatExample;
import com.e3.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Override
    public List<EasyUITree> findItemCatList(Long parentId) {
        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(example);
        List<EasyUITree> easyUITrees=new ArrayList<>();
        for (TbItemCat cat:tbItemCats){
            EasyUITree easyUITree=new EasyUITree();
            easyUITree.setId(cat.getId());
            easyUITree.setText(cat.getName());
            easyUITree.setState(cat.getIsParent()?"closed":"open");
            easyUITrees.add(easyUITree);
        }
        return easyUITrees;
    }
}
