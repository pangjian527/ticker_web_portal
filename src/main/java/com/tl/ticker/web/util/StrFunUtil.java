package com.tl.ticker.web.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by pangjian on 16-12-2.
 */
public class StrFunUtil {

    public static int valueInt(String value){
        return Integer.valueOf(value);
    }

    public static int valueInt(String value,int defaultValue){

        if(StringUtils.isBlank(value)){
            return defaultValue;
        }

        return Integer.valueOf(value);
    }

}
