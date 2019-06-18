package com.e3.service;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.EasyUIDataGrid;
import com.e3.pojo.TbItem;

public interface ItemService {

    TbItem findTbItemById(Long id);

    EasyUIDataGrid findTbItemList(Integer page,Integer rows);
    E3Result addItem(TbItem item, String desc);
    E3Result findItemDescById(Long id);

    E3Result updateItemById(TbItem item,String desc);

    E3Result deleteItemsByIds(String ids);



}
