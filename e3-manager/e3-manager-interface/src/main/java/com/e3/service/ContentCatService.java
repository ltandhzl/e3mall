package com.e3.service;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.EasyUITree;
import com.e3.pojo.TbContentCategory;

import java.util.List;

public interface ContentCatService {

    List<EasyUITree> findContentCatList(Long parentId);

    E3Result addContentCat(Long parentId,String name);

    E3Result updateContentCat(TbContentCategory category);
}
