package com.e3.service;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.EasyUIDataGrid;
import com.e3.pojo.TbContent;

import java.util.List;

public interface ContentService {

    E3Result addContent(TbContent content);

    EasyUIDataGrid findContentList(Long categoryId,Integer page,Integer rows);

    List<TbContent> findContentByCatId(Long categoryId);
}
