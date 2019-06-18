package com.e3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "{path}")
    public String path(@PathVariable String path){
        return path;
    }
}
