package com.e3.service;


import com.e3.common.pojo.EasyUITree;


import java.util.List;

public interface ItemCatService {

    List<EasyUITree> findItemCatList(Long parentId);



}
