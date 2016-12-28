package com.tl.ticker.web.action.entity;

import com.tl.rpc.order.Order;

import java.util.Date;

/**
 * Created by pangjian on 16-12-7.
 */
public class OrderItemResult {

    private String id;

    private double amount;

    private String userId;

    private String productId;

    private String result;

    private Date createTime;

    private Date updateTime;

    private String status;

    private int year;

    private int stage ;

    private String title;

    private String mobile;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public static OrderItemResult fromOrder(Order order){
        OrderItemResult itemResult = new OrderItemResult();

        itemResult.setProductId(order.getProductId());
        itemResult.setId(order.getId());
        itemResult.setResult(order.getResult());
        itemResult.setYear(order.getYear());
        itemResult.setAmount(order.getAmount());
        itemResult.setCreateTime(new Date(order.getCreateTime()));
        itemResult.setStage(order.getStage());
        itemResult.setStatus(order.getStatus().name());
        itemResult.setUserId(order.getUserId());
        return itemResult;
    }
}
