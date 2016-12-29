package com.tl.ticker.web.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by pangjian on 16-12-29.
 */
public class SmsUtil {


    private static final String account = "fx2010";
    private static final String pwd = "chun19900103";
    private static final String userid= "1908";

    private static final String url = "http://106.3.37.116:8888/sms.aspx";

    private static String templates = "【皇家团购】您的短信验证码是：%s,请在页面输入完成验证！";

    public static boolean sendSms(String mobile,String content){

        Map<String,String> map = new HashMap<String, String>();
        map.put("action","send");
        map.put("userid",userid);
        map.put("account",account);
        map.put("password",pwd);
        map.put("mobile",mobile);
        map.put("content",content);
        map.put("sendTime","");
        map.put("extno","");

        post(url,map);

        return true;
    }

    public static void main(String[] args){

        //SmsUtil.sendSms("18922378741","【皇家团购】你的验证编码是：0837，请尽快填写。");
    }


    public static String post(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String body = null;

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity entity = response.getEntity();
        return body;
    }

    public static String getSmsContent(int random){
        return String.format(templates, random);
    }

    public static int getRandom(){

        return (int) (Math.random() * 10000);
    }
}
