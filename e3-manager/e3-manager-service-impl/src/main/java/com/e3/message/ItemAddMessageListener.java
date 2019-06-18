package com.e3.message;

import com.e3.common.pojo.SolrJItem;
import com.e3.mapper.SolrJItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private SolrJItemMapper solrJItemMapper;

    @Autowired
    private SolrServer solrServer;
    @Override
    public void onMessage(Message message){
        TextMessage textMessage=(TextMessage) message;
        try {
            String id = textMessage.getText();
            //等待事务提交
            Thread.sleep(1000);
            SolrJItem solrJItem = solrJItemMapper.findItenById(Long.parseLong(id));
            SolrInputDocument document=new SolrInputDocument();
            document.addField("id", solrJItem.getId());
            document.addField("item_title", solrJItem.getTitle());
            document.addField("item_sell_point", solrJItem.getSell_point());
            document.addField("item_price", solrJItem.getPrice());
            document.addField("item_image", solrJItem.getImage());
            document.addField("item_category_name", solrJItem.getCategory_name());
            solrServer.add(document);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
