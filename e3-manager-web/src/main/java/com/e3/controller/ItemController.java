package com.e3.controller;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.EasyUIDataGrid;
import com.e3.pojo.TbItem;
import com.e3.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Autowired
    private  ItemService itemService;


//    @RequestMapping(value = "/item/{path}")
//    public @ResponseBody
//    TbItem item(@PathVariable Long path){
//        return itemService.findTbItemById(path);
//    }

    @RequestMapping(value = "/item/list")
    public @ResponseBody
    EasyUIDataGrid itemList(Integer page,Integer rows){
        return itemService.findTbItemList(page,rows);
    }

    @RequestMapping(value = "/item/save")
    public @ResponseBody
    E3Result addItem(TbItem item,String desc){
        E3Result e3Result = itemService.addItem(item, desc);
        return e3Result;
    }

    @RequestMapping(value = "/item/desc/{id}")
    public @ResponseBody
    E3Result itemDescById(@PathVariable Long id){
        E3Result result = itemService.findItemDescById(id);
        return result;
    }

    @RequestMapping(value = "/item/update")
    public @ResponseBody E3Result updateItem(TbItem item,String desc){

        E3Result e3Result = itemService.updateItemById(item, desc);
        return e3Result;
    }
    @RequestMapping(value = "/item/delete")
    public @ResponseBody E3Result deleteItems(String ids){

        E3Result e3Result = itemService.deleteItemsByIds(ids);
        return e3Result;
    }



}
