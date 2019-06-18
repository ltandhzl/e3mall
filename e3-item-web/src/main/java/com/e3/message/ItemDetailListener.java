package com.e3.message;

import com.e3.pojo.Item;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemDesc;
import com.e3.service.ItemDetailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ItemDetailListener implements MessageListener {
    @Autowired
    private ItemDetailService itemDetailService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage= (TextMessage) message;
            String id = textMessage.getText();
            Long itemId=Long.parseLong(id);
            TbItem tbItem = itemDetailService.findTbItemById(itemId);
            Item item=new Item(tbItem);
            TbItemDesc tbItemDesc = itemDetailService.findTbItenDescById(itemId);
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map data=new HashMap();
            data.put("item",item);
            data.put("itemDesc",tbItemDesc);
            Writer out = new FileWriter(new File("D:\\freemarker\\item\\"+tbItem.getId()+".html"));
            template.process(data, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
