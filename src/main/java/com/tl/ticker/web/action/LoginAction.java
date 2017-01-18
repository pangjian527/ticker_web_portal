package com.tl.ticker.web.action;

import com.tl.rpc.common.ServiceToken;
import com.tl.rpc.consumer.Consumer;
import com.tl.rpc.consumer.ConsumerService;
import com.tl.rpc.msg.Msg;
import com.tl.rpc.msg.MsgService;
import com.tl.ticker.web.action.entity.ResultJson;
import com.tl.ticker.web.common.Constant;
import com.tl.ticker.web.util.JsonUtil;
import com.tl.ticker.web.util.SmsUtil;
import com.tl.ticker.web.util.ValidateCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by pangjian on 16-12-19.
 */
@Controller
public class LoginAction {

    @RequestMapping("/portal/login")
    public String execute(Model model, HttpSession session){

        ValidateCodeUtil valiCode = new ValidateCodeUtil(130,40,4,150);

        model.addAttribute("valiBase64Image",valiCode.getBase64Code());
        session.setAttribute(Constant.VALID_CODE,valiCode.getCode());

        return "login";
    }

    @ResponseBody
    @RequestMapping("/portal/login/submit")
    public String login(HttpSession session, String mobile, String pwd, String validCode) {

        if(StringUtils.isBlank(mobile)){
            return JsonUtil.toString(new ResultJson(false,"手机号码必填"));
        }else  if(StringUtils.isBlank(pwd)){
            return JsonUtil.toString(new ResultJson(false,"请填写密码"));
        }else if(StringUtils.isBlank(validCode)) {
            System.out.println(JsonUtil.toString(new ResultJson(false, "请填写验证码")));
            return JsonUtil.toString(new ResultJson(false, "请填写验证码"));
        }

        Object valieObj = session.getAttribute(Constant.VALID_CODE);

        if(valieObj==null || !valieObj.toString().equalsIgnoreCase(validCode)){
            return JsonUtil.toString(new ResultJson(false, "验证码不正确"));
        }

        Consumer consumer = null ;
        try{
            consumer = consumerService.getByMobile(new ServiceToken(),mobile);
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.toString(new ResultJson(false, "用户不存在"));
        }

        if(!pwd.equals(consumer.getPwd())){
            return JsonUtil.toString(new ResultJson(false, "密码错误"));
        }

        //缓存用户信息
        session.setAttribute(Constant.SESSION_USER,consumer);
        return JsonUtil.toString(new ResultJson(true));
    }

    @RequestMapping("/portal/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/portal/login";
    }

    @ResponseBody
    @RequestMapping("/portal/login/check")
    public String checkLogin(HttpSession session){

        Object consumer = session.getAttribute(Constant.SESSION_USER);

        if(consumer == null){
            return JsonUtil.toString(new ResultJson(false));
        }else{
            return JsonUtil.toString(new ResultJson(true));
        }
    }

    @RequestMapping("/portal/login/switchcode")
    @ResponseBody
    public String switchImageCode(Model model,HttpSession session,int width){
        ValidateCodeUtil valiCode = new ValidateCodeUtil(width,40,4,150);

        session.setAttribute(Constant.VALID_CODE,valiCode.getCode());

        return JsonUtil.toString(new ResultJson(true, valiCode.getBase64Code()));
    }

    @RequestMapping("/portal/login/sendSms")
    @ResponseBody
    public String sendSms(HttpSession session,String mobile,String validCode) throws Exception{

        String code = session.getAttribute(Constant.VALID_CODE).toString();

        if(!code.equalsIgnoreCase(validCode)){
            return JsonUtil.toString(new ResultJson(false, "验证码不正确"));
        }

        int random = SmsUtil.getRandom();
        String smsContent = SmsUtil.getSmsContent(random);
        SmsUtil.sendSms(mobile,smsContent);

        session.setAttribute(Constant.SMS_VALID_CODE,random);

        Msg msg = new Msg();
        msg.setMobile(mobile);
        msg.setCreateTime(new Date().getTime());
        msg.setContent(smsContent);

        msgService.sendMsg(new ServiceToken(),msg);

        return JsonUtil.toString(new ResultJson(true));
    }

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private MsgService msgService;
}
