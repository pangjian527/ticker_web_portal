package com.tl.ticker.web.action.entity;

import com.tl.rpc.recharge.Recharge;

import java.util.Date;

/**
 * Created by pangjian on 16-12-8.
 */
public class RechargeResult {

    public String id;

    public String userId;

    public double amount;

    public double giveAmount;

    public Date createTime;

    public String source;

    public String mobile;
    
    public static RechargeResult formRecharge(Recharge recharge){

        RechargeResult rechargeResult = new RechargeResult();
        rechargeResult.id = recharge.getId();
        rechargeResult.userId = recharge.getUserId();
        rechargeResult.amount = recharge.getAmount();
        rechargeResult.giveAmount = recharge.getGiveAmount();
        rechargeResult.createTime = new Date(recharge.getCreateTime());
        rechargeResult.source = recharge.getSource();

        return rechargeResult;
    }
    
}
