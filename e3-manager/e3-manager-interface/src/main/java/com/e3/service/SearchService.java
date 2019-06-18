package com.e3.service;

import com.e3.common.pojo.SearchResult;

public interface SearchService {

     SearchResult findSearchList(String keywords,Integer page,Integer rows) throws Exception;
}
