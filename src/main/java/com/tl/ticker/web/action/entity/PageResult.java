package com.tl.ticker.web.action.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pangjian on 16-12-2.
 */
public class PageResult {

    //总行数
    private int totalCount;

    //每一页的数量
    private int limit;

    private List<String> pageUrls;

    private String url;

    public PageResult(int totalCount, int limit, int currentOffset, String url){
        this.totalCount = totalCount;
        this.limit = limit;
        this.url = url;
        this.pageUrls = new LinkedList<String>();
    }

    /* 获取分页的总页数 */
    public int getPageCount(){
        return (int)Math.ceil(totalCount/(limit*1.0));
    }

    /**/
    public int getTotalCount(){
        return totalCount;
    }

    public List<String> getPageUrls(){
        for (int i=0 ;i<this.getPageCount();i++){
            this.pageUrls.add(url + "?offset=" + (i*this.limit));
        }
        return this.pageUrls;
    }

}
