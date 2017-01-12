package com.tl.ticker.web.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pangjian on 16-12-1.
 */
public class Constant {

    public final static String VALID_CODE = "validCode";

    public final static String SMS_VALID_CODE = "smsValidCode";

    public final static String SESSION_USER = "session_user_code";

    public static String getSizeType(String sizeCode){

        Map<String,String> map = new HashMap<String, String>();
        map.put("small","小");
        map.put("big","大");
        map.put("single","单");
        map.put("double","双");

        return map.get(sizeCode);
    }

    public static String getColorType(String colorCode){

        Map<String,String> map = new HashMap<String, String>();
        map.put("red","红波");
        map.put("green","绿波");
        map.put("blue","蓝波");

        return map.get(colorCode);
    }
}
