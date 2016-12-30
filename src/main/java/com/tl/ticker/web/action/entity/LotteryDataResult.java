package com.tl.ticker.web.action.entity;

import com.tl.rpc.lottery.LotteryData;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by pangjian on 16-12-21.
 */
public class LotteryDataResult {

    public String id;
    public int stage;
    public int year;
    public int number;
    public Date lotteryDate;
    public String colorCode;
    public String zodiacCode;
    public int flatNumber1;
    public int flatNumber2;
    public int flatNumber3;
    public int flatNumber4;
    public int flatNumber5;
    public int flatNumber6;

    public String colorCode1;
    public String colorCode2;
    public String colorCode3;
    public String colorCode4;
    public String colorCode5;
    public String colorCode6;

    public String week ;

    public static LotteryDataResult fromLotteryDataResult(LotteryData lotteryData){

        LotteryDataResult result = new LotteryDataResult();
        result.id = lotteryData.getId();
        result.stage = lotteryData.getStage();
        result.year = lotteryData.getYear();
        result.number = lotteryData.getNumber();
        result.lotteryDate = new Date(lotteryData.getLotteryTime());
        result.flatNumber1 = lotteryData.getFlatNumber1();
        result.flatNumber2 = lotteryData.getFlatNumber2();
        result.flatNumber3 = lotteryData.getFlatNumber3();
        result.flatNumber4 = lotteryData.getFlatNumber4();
        result.flatNumber5 = lotteryData.getFlatNumber5();
        result.flatNumber6 = lotteryData.getFlatNumber6();
        result.week = getWeekOfDate(result.lotteryDate);

        return result;
    }

    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }
    
}
