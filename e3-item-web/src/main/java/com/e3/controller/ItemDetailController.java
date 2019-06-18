package com.e3.controller;

import com.e3.pojo.Item;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemDesc;
import com.e3.service.ItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemDetailController {

    @Autowired
    private ItemDetailService itemDetailService;

    @RequestMapping(value = "/item/{id}")
    public String itemDetail(@PathVariable Long id, Model model){
        TbItem tbItem = itemDetailService.findTbItemById(id);
        Item item=new Item(tbItem);
        TbItemDesc tbItemDesc = itemDetailService.findTbItenDescById(id);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",tbItemDesc);
        return "item";
    }
}
