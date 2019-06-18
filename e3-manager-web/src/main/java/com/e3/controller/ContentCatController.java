package com.e3.controller;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.EasyUITree;
import com.e3.pojo.TbContentCategory;
import com.e3.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCatController {

    @Autowired
    private ContentCatService contentCatService;


    @RequestMapping(value = "/content/category/list")
    public @ResponseBody
    List<EasyUITree> contentCatList(@RequestParam(name="id", defaultValue="0") Long parentId){
        return contentCatService.findContentCatList(parentId);
    }

    @RequestMapping(value = "/content/category/add")
    public @ResponseBody
    E3Result contentCatAdd(Long parentId,String name){

        E3Result e3Result = contentCatService.addContentCat(parentId, name);
        return e3Result;
    }

    @RequestMapping(value = "/content/category/update")
    public @ResponseBody E3Result contentCatUpdate(TbContentCategory category){
        E3Result e3Result = contentCatService.updateContentCat(category);
        return e3Result;
    }


}
