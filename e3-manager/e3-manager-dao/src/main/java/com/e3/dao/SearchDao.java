package com.e3.dao;


import com.e3.common.pojo.SearchResult;
import com.e3.common.pojo.SolrJItem;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;
    public SearchResult search(SolrQuery query) throws Exception {

        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        List<SolrJItem> solrJItems=new ArrayList<>();
        for (SolrDocument document:results){
            SolrJItem solrJItem=new SolrJItem();
            solrJItem.setId((String) document.get("id"));
            solrJItem.setCategory_name((String) document.get("item_category_name"));
            solrJItem.setImage((String) document.get("item_image"));
            solrJItem.setSell_point((String) document.get("item_sell_point"));
            solrJItem.setCategory_name((String) document.get("item_category_name"));
            solrJItem.setPrice((Long) document.get("item_price"));
            Map<String, List<String>> listMap = highlighting.get(document.get("id"));
            String title=null;
            if (listMap!=null && listMap.size()>0){
                List<String> itemTitle = listMap.get("item_title");
                if (itemTitle!=null && itemTitle.size()>0){
                    title=itemTitle.get(0);
                }else{
                    title= (String) document.get("item_title");
                }
            }else{
                title= (String) document.get("item_title");
            }
            solrJItem.setTitle(title);
            solrJItems.add(solrJItem);
        }
        SearchResult searchResult=new SearchResult();
        searchResult.setItemList(solrJItems);
        searchResult.setRecordCount(results.getNumFound());
        return searchResult;

    }
}
