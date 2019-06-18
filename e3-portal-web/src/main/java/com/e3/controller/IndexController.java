package com.e3.controller;

import com.e3.pojo.TbContent;
import com.e3.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    @Value("${BIG_AD_ID}")
    private String BIG_AD_ID;

    @RequestMapping(value = "/index.html")
    public String toIndex(Model model){

        List<TbContent> ad1 = contentService.findContentByCatId(Long.parseLong(BIG_AD_ID));
        model.addAttribute("ad1",ad1);
        return "index";
    }


}
