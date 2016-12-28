package com.tl.ticker.web.action.entity;

import org.springframework.ui.Model;

/**
 * Created by pangjian on 16-12-1.
 */
public class ResultJson {

    private boolean isOk;

    private String result;

    private String code;

    public ResultJson(){

    }

    public ResultJson(boolean isOk, String result, String code){
        this.isOk = isOk;
        this.result = result;
        this.code = code;
    }

    public ResultJson(boolean isOk){
        this.isOk = isOk;
    }

    public ResultJson(boolean isOk, String result){
        this.isOk = isOk;
        this.result = result;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String returnSuccess(String content,Model model){
        model.addAttribute("content",content);
        return "/common/result";
    }
}
