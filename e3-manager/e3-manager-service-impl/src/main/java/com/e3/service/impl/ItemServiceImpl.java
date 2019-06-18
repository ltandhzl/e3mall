package com.e3.service.impl;

import com.e3.common.pojo.E3Result;
import com.e3.common.pojo.EasyUIDataGrid;
import com.e3.common.pojo.IDUtils;
import com.e3.mapper.TbItemDescMapper;
import com.e3.mapper.TbItemMapper;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemDesc;
import com.e3.pojo.TbItemExample;
import com.e3.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination topicDestination;

    @Override
    public TbItem findTbItemById(Long id) {

        return tbItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public EasyUIDataGrid findTbItemList(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        TbItemExample example=new TbItemExample();
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        EasyUIDataGrid dataGrid=new EasyUIDataGrid();
        dataGrid.setRows(tbItems);
        PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(tbItems);
        long total = pageInfo.getTotal();
        dataGrid.setTotal(total);

        return dataGrid;
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {

        final long id = IDUtils.genItemId();
        item.setId(id);
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        tbItemMapper.insert(item);
        TbItemDesc tbItemDesc=new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemDesc(desc);
        tbItemDescMapper.insert(tbItemDesc);
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(id+"");
            }
        });
        return E3Result.ok();
    }
    @Override
    public E3Result findItemDescById(Long id) {
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
        E3Result e3Result=null;
        if (tbItemDesc!=null){
            e3Result= new E3Result(tbItemDesc);
        }else{
            e3Result=new E3Result(404,"id错误",null);
        }

        return e3Result;
    }

    @Override
    public E3Result updateItemById(TbItem item,String desc) {

        item.setUpdated(new Date());
        tbItemMapper.updateByPrimaryKeySelective(item);
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(item.getId());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(new Date());
        tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);

        return E3Result.ok();
    }

    @Override
    public E3Result deleteItemsByIds(String ids) {

        String[] split = ids.split(",");

        for (String str:split){
            long id = Long.parseLong(str);
            tbItemMapper.deleteItemById(id);
        }

        return E3Result.ok();
    }
}
