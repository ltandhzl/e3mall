package com.e3.mapper;

import com.e3.common.pojo.SolrJItem;

import java.util.List;

public interface SolrJItemMapper {

    List<SolrJItem> findItemList();
    SolrJItem findItenById(Long id);
}
