package com.e3.service.impl;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.SolrJItem;
import com.e3.mapper.SolrJItemMapper;
import com.e3.service.SolrJImportService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SolrJImportServiceImpl implements SolrJImportService {


    @Autowired
    private SolrJItemMapper solrJItemMapper;

    @Autowired
    private SolrServer solrServer;
    @Override
    public E3Result importSolrJData() {

        try {
            List<SolrJItem> solrJItems = solrJItemMapper.findItemList();
            for (SolrJItem solrJItem:solrJItems){
                SolrInputDocument document=new SolrInputDocument();
                document.addField("id",solrJItem.getId());
                document.addField("item_title",solrJItem.getTitle());
                document.addField("item_price",solrJItem.getPrice());
                document.addField("item_image",solrJItem.getImage());
                document.addField("item_sell_point",solrJItem.getSell_point());
                document.addField("item_category_name",solrJItem.getCategory_name());
                solrServer.add(document);
            }
            solrServer.commit();
            return E3Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500,"数据导入索引库失败！");
        }

    }
}
