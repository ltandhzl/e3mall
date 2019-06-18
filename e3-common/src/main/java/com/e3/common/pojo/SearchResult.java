package com.e3.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    private long recordCount;
    private int totalPages;
    private List<SolrJItem> itemList;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SolrJItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SolrJItem> itemList) {
        this.itemList = itemList;
    }
}
