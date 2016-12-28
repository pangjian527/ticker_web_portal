package com.tl.ticker.web.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by pangjian on 16-12-1.
 */
public class JsonUtil {

    public static String toString(Object object){
        if(object instanceof List){
            return JSONArray.fromObject(object).toString();
        }else{
            return JSONObject.fromObject(object).toString();
        }
    }
}
