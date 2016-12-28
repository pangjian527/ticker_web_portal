package com.tl.ticker.web.action;

import com.tl.rpc.common.ServiceToken;
import com.tl.rpc.consumer.Consumer;
import com.tl.rpc.consumer.ConsumerService;
import com.tl.rpc.order.ORDERSTATUS;
import com.tl.rpc.order.Order;
import com.tl.rpc.order.OrderService;
import com.tl.rpc.product.Product;
import com.tl.rpc.product.ProductService;
import com.tl.ticker.web.action.entity.ResultJson;
import com.tl.ticker.web.common.Constant;
import com.tl.ticker.web.util.JsonUtil;
import com.tl.ticker.web.util.ValidateCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by pangjian on 16-12-20.
 */
@Controller
public class BuyAction {

    @RequestMapping("/portal/buy")
    public String execute(String id, Model model, HttpSession session) throws Exception{

        Object consumer = session.getAttribute(Constant.SESSION_USER);
        Product product = productService.getByProductId(new ServiceToken(), id);

        ValidateCodeUtil valiCode = new ValidateCodeUtil(95,40,4,150);

        model.addAttribute("valiBase64Image","data:image/jpg;base64"+valiCode.getBase64Code());
        session.setAttribute(Constant.VALID_CODE,valiCode.getCode());

        model.addAttribute("product",product);
        model.addAttribute("consumer",consumer);
        return "buy";
    }

    @ResponseBody
    @RequestMapping("/portal/buy/submit")
    public String submit(HttpSession session,Model model,
                         String productId,
                         String validCode,String smsCode) throws Exception{

         if(StringUtils.isBlank(validCode)){
            return JsonUtil.toString(new ResultJson(false,"请填写图形验证码"));
        }else if(StringUtils.isBlank(smsCode)) {
            return JsonUtil.toString(new ResultJson(false, "请填写短信验证码"));
        }

        String code = session.getAttribute(Constant.VALID_CODE).toString();
        if(!code.equalsIgnoreCase(validCode)){
            return JsonUtil.toString(new ResultJson(false, "验证码不正确"));
        }

        ServiceToken token = new ServiceToken();
        Product product = productService.getByProductId(token, productId);

        Consumer consumer =(Consumer) session.getAttribute(Constant.SESSION_USER);

        if(consumer.getBalance() < product.getBalance()){
            return JsonUtil.toString(new ResultJson(false, "余额不足，请充值"));
        }

        Order order = new Order();
        order.setProductId(productId);
        order.setCreateTime(new Date().getTime());
        order.setStatus(ORDERSTATUS.COMPLETE);
        order.setStage(product.getStage());
        order.setYear(product.getYear());
        order.setAmount(product.getBalance());
        order.setUserId(consumer.getId());
        orderService.saveOrder(token,order);

        consumer.setBalance(consumer.getBalance()-product.getBalance());
        consumerService.saveConsumer(token,consumer);

        return JsonUtil.toString(new ResultJson(true, "购买成功"));
    }

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ConsumerService consumerService;
}
