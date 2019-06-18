package com.e3.service;

import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemDesc;

public interface ItemDetailService {

    TbItem findTbItemById(Long id);

    TbItemDesc findTbItenDescById(Long id);
}
