package com.tl.ticker.web.action.entity;

import java.util.*;

/**
 * Created by pangjian on 16-12-2.
 */
public class PageResult {

    //总行数
    private int totalCount;

    //每一页的数量
    private int limit;

    private Map<Integer,String> pageUrls;

    private String url;

    private int currentPage;

    public PageResult(int totalCount, int limit, int currentOffset, String url){
        this.totalCount = totalCount;
        this.limit = limit;
        this.url = url;
        this.pageUrls = new LinkedHashMap<Integer, String>();

        this.currentPage = currentOffset/limit;
    }

    /* 获取分页的总页数 */
    public int getPageCount(){
        return (int)Math.ceil(totalCount/(limit*1.0));
    }

    /**/
    public int getTotalCount(){
        return totalCount;
    }

    public Map<Integer,String> getPageUrls(){

        int pageCount = this.getPageCount();

        for (int i=0 ;i<pageCount;i++){

            if(pageCount <= 6 ){
                if(url.indexOf("?")>=0){
                    this.pageUrls.put(i,url + "&offset=" + (i*this.limit));
                }else{
                    this.pageUrls.put(i,url + "?offset=" + (i*this.limit));
                }
            }else {

                if(i<3 || i==( pageCount-1)){
                    if(url.indexOf("?")>=0){
                        this.pageUrls.put(i,url + "&offset=" + (i*this.limit));
                    }else{
                        this.pageUrls.put(i,url + "?offset=" + (i*this.limit));
                    }
                }else {

                    if(this.currentPage > 5){
                        this.pageUrls.put(-1, "more");
                    }

                    if(this.currentPage >=2){
                        if (url.indexOf("?") >= 0) {
                            this.pageUrls.put((this.currentPage - 2), url + "&offset=" + ((this.currentPage - 2) * this.limit));
                            this.pageUrls.put((this.currentPage - 1), url + "&offset=" + ((this.currentPage - 1) * this.limit));
                            this.pageUrls.put((this.currentPage ), url + "&offset=" + ((this.currentPage) * this.limit));
                            if(this.currentPage != (pageCount-1))
                            this.pageUrls.put((this.currentPage +1), url + "&offset=" + ((this.currentPage + 1) * this.limit));
                        } else {
                            this.pageUrls.put((this.currentPage -2), url + "?offset=" + ((this.currentPage - 2) * this.limit));
                            this.pageUrls.put((this.currentPage -1), url + "?offset=" + ((this.currentPage - 1) * this.limit));
                            this.pageUrls.put((this.currentPage ), url + "?offset=" + ((this.currentPage) * this.limit));
                            if(this.currentPage != (pageCount-1))
                            this.pageUrls.put((this.currentPage +1), url + "?offset=" + ((this.currentPage + 1) * this.limit));
                        }
                    }

                    if(this.currentPage <pageCount-2){
                        this.pageUrls.put(-1, "more");
                    }
                    i = pageCount -2;
                }
            }

        }
        return this.pageUrls;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
