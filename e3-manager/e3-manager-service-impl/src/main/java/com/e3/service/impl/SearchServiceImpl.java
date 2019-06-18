package com.e3.service.impl;

import com.e3.common.pojo.SearchResult;
import com.e3.dao.SearchDao;
import com.e3.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult findSearchList(String keywords, Integer page, Integer rows) throws Exception {
        SolrQuery query=new SolrQuery();
        query.set("q",keywords);
        query.set("df","item_title");
        if (page<=0) page=1;
        query.setStart((page-1)*rows);
        query.setRows(rows);
        query.setHighlight(true);
        query.setHighlightSimplePre("<span style='color:red'>");
        query.setHighlightSimplePost("</span>");
        SearchResult searchResult= searchDao.search(query);
        long recordCount = searchResult.getRecordCount();
        int totalPage= (int) Math.ceil(recordCount/rows);
        searchResult.setTotalPages(totalPage);
        return searchResult;
    }
}
