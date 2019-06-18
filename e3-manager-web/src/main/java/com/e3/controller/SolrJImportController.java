package com.e3.controller;

import com.e3.common.pojo.E3Result;
import com.e3.service.SolrJImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SolrJImportController {

    @Autowired
    private SolrJImportService solrJImportService;
    @RequestMapping(value = "/index/item/import")
    @ResponseBody
    public E3Result importData(){

        E3Result e3Result = solrJImportService.importSolrJData();

        return e3Result;
    }
}
