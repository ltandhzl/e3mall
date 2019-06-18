package com.e3.controller;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.EasyUIDataGrid;
import com.e3.pojo.TbContent;
import com.e3.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/add")
    public @ResponseBody
    E3Result contentAdd(TbContent content){
        E3Result e3Result = contentService.addContent(content);
        return e3Result;
    }
    @RequestMapping(value = "/content/list")
    public @ResponseBody
    EasyUIDataGrid contentList(Long categoryId,Integer page,Integer rows){
        return contentService.findContentList(categoryId,page,rows);

    }
}
