package com.tl.ticker.web.action.entity;

import com.tl.rpc.lottery.LotteryData;

import java.util.Date;

/**
 * Created by pangjian on 16-12-21.
 */
public class LotteryDataResult {

    public String id;
    public String baseDataId;
    public int stage;
    public int year;
    public int number;
    public Date lotteryDate;
    public String colorCode;
    public String zodiacCode;

    public static LotteryDataResult fromLotteryDataResult(LotteryData lotteryData){

        LotteryDataResult result = new LotteryDataResult();
        result.id = lotteryData.getId();
        result.baseDataId = lotteryData.getBaseDataId();
        result.stage = lotteryData.getStage();
        result.year = lotteryData.getYear();
        result.number = lotteryData.getNumber();
        result.lotteryDate = new Date(lotteryData.getLotteryTime());

        return result;
    }
    
}
