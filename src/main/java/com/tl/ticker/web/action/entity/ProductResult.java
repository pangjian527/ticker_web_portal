package com.tl.ticker.web.action.entity;

import com.tl.rpc.product.Product;
import com.tl.ticker.web.common.Constant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * Created by pangjian on 16-12-7.
 */
public class ProductResult {

    public String id;

    public String expect;

    public double balance;

    public Date createTime;

    public int year;

    public int stage;

    public Date updateTime;

    public String status;

    public String title;

    private String mobile;

    public int virtualCount;

    public String probability;

    public int saleCount;

    public int limitCount;

    public static ProductResult fromProductResult(Product product){

        ProductResult result = new ProductResult();
        result.balance = product.getBalance();
        result.createTime = new Date(product.getCreateTime());
        result.expect = product.getExpect();
        result.id = product.getId();
        result.mobile = product.getMobile();
        result.stage = product.getStage();
        result.title = product.getTitle();
        result.updateTime = new Date(product.getUpdateTime());
        result.year = product.getYear();
        result.status = product.getStatus().name();
        result.virtualCount = product.getVirtualCount();
        result.probability = product.getProbability();
        result.limitCount = product.getLimitCount();

        return result;
    }

    public String getMobile() {

        String preMobile = this.mobile.substring(0, 3);
        String nextMobile = this.mobile.substring(7);

        return preMobile+"****"+nextMobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSendSmsContent(){
        StringBuilder content = new StringBuilder("【皇家团购】");
        JSONObject object = JSONObject.fromObject(this.expect);

        if(object.getInt("type") ==0){
            JSONArray jsonArray = object.getJSONArray("items");
            for (int i=0 ;i<jsonArray.size();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                content.append(jsonObject.getString("name")+":");
                JSONArray numbers = jsonObject.getJSONArray("numbers");

                for (int j=0 ;j<numbers.size();j++){
                    content.append(numbers.getInt(j)).append("、");
                }
            }
            return content.toString();
        }else if (object.getInt("type") ==1){

            String sizeType = object.getString("sizeType");
            return content.append("彩票类型（大小单双）："+ Constant.getSizeType(sizeType)).toString();

        }else if (object.getInt("type") == 2){
            JSONArray array = object.getJSONArray("colorType");

            String result = "";
            for (int i=0 ;i<array.size();i++){
                result += Constant.getColorType(array.getString(i))+"、";
            }
            return content.append("波色类型："+result).toString();
        }else if (object.getInt("type") == 3){
            JSONArray array = object.getJSONArray("zodiacType");

            String result = "";
            for (int i=0 ;i<array.size();i++){
                result += array.getString(i)+"、";
            }
            return "生肖类型："+result+"";
        }
        return "请联系管理员";
    }

}
