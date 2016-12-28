package com.tl.ticker.web.action;

import com.tl.rpc.common.ServiceToken;
import com.tl.rpc.consumer.Consumer;
import com.tl.rpc.consumer.ConsumerService;
import com.tl.rpc.order.Order;
import com.tl.rpc.order.OrderResult;
import com.tl.rpc.order.OrderService;
import com.tl.rpc.product.Product;
import com.tl.rpc.product.ProductService;
import com.tl.rpc.recharge.Recharge;
import com.tl.rpc.recharge.RechargeService;
import com.tl.rpc.recharge.SearchResult;
import com.tl.ticker.web.action.entity.OrderItemResult;
import com.tl.ticker.web.action.entity.RechargeResult;
import com.tl.ticker.web.common.Constant;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pangjian on 16-12-24.
 */
@Controller
public class CenterAction {

    @RequestMapping("/portal/center")
    public String execute(Model model, HttpSession session, HttpServletRequest request,String code) throws Exception{

        Object object = session.getAttribute(Constant.SESSION_USER);
        if(object == null){
            return "redirect:/portal/login";
        }

        Consumer consumer = (Consumer)object;

        if("recharge".equals(code)){
            List<RechargeResult> rechargeList = this.getRechargeList(consumer);
            model.addAttribute("rechargeList",rechargeList);
        }else{
            List<OrderItemResult> orderList = this.getOrderList(consumer);
            model.addAttribute("orderList",orderList);
        }

        model.addAttribute("consumer",consumer);

        return "center/person";
    }


    private List<OrderItemResult> getOrderList(Consumer consumer) throws Exception{
        ServiceToken token = new ServiceToken();

        OrderResult orderResult = orderService.searchOrderByUserId(token, 200, 0,consumer.getId());

        List<OrderItemResult> listResult = new LinkedList<OrderItemResult>();

        for (Order order :orderResult.getResult()) {

            OrderItemResult itemResult = OrderItemResult.fromOrder(order);

            Product product = productService.getByProductId(token, order.getProductId());
            itemResult.setTitle(product.getTitle());

            itemResult.setMobile(consumer.getMobile());

            listResult.add(itemResult);
        }
        return listResult;
    }

    public List<RechargeResult> getRechargeList(Consumer consumer) throws Exception{
        ServiceToken token = new ServiceToken();
        SearchResult searchResult = rechargeService.searchRecharge(token, 50, 0, consumer.getId());

        List<RechargeResult> listResult = new LinkedList<RechargeResult>();
        for (Recharge recharge :searchResult.getResult()) {

            RechargeResult rechargeResult = RechargeResult.formRecharge(recharge);

            rechargeResult.mobile = consumer.getMobile();
            listResult.add(rechargeResult);
        }
        return listResult;
    }


    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private RechargeService rechargeService;

}
