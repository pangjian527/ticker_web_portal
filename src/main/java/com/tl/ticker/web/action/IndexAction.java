package com.tl.ticker.web.action;

import com.tl.rpc.base.BaseData;
import com.tl.rpc.base.BaseDataService;
import com.tl.rpc.common.ServiceToken;
import com.tl.rpc.consumer.Consumer;
import com.tl.rpc.consumer.ConsumerService;
import com.tl.rpc.lottery.LotteryData;
import com.tl.rpc.lottery.LotteryDataService;
import com.tl.rpc.lottery.SearchResult;
import com.tl.rpc.order.OrderService;
import com.tl.rpc.product.Product;
import com.tl.rpc.product.ProductService;
import com.tl.rpc.product.SearchProductResult;
import com.tl.rpc.topic.SearchTopicResult;
import com.tl.rpc.topic.TOPICSTATUS;
import com.tl.rpc.topic.Topic;
import com.tl.rpc.topic.TopicService;
import com.tl.ticker.web.action.entity.LotteryDataResult;
import com.tl.ticker.web.action.entity.ProductResult;
import com.tl.ticker.web.action.entity.TopicResult;
import com.tl.ticker.web.common.Constant;
import com.tl.ticker.web.util.StrFunUtil;
import com.tl.ticker.web.util.ValidateCodeUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pangjian on 16-12-17.
 */
@Controller
public class IndexAction {

    @RequestMapping("/portal")
    public String execute(Model model, HttpServletRequest request, HttpSession session) throws Exception{

        int offset = StrFunUtil.valueInt(request.getParameter("offset"),0);
        int limit = StrFunUtil.valueInt(request.getParameter("limit"),10);

        //1. 获取商品资料数据
        ServiceToken token = new ServiceToken();
        SearchProductResult searchProductResult = productService.searchProduct(token, limit, offset);

        List<ProductResult> listResult = new LinkedList<ProductResult>();
        for (Product product : searchProductResult.getResult()) {

            ProductResult productResult = ProductResult.fromProductResult(product);
            int count = orderService.totalCountByProductId(token, product.getId());
            productResult.saleCount = count + product.getVirtualCount();

            listResult.add(productResult);
        }

        //2. 获取论坛数据
        SearchTopicResult topicResultList = topicService.searchTopic(token, 30, 0, TOPICSTATUS.OPEN);

        List<TopicResult> listTopicResult = new LinkedList<TopicResult>();
        for (Topic topic : topicResultList.getResult()) {

            Consumer consumer = consumerService.getById(token, topic.getUserId());
            TopicResult topicResult = TopicResult.fromTopicResult(topic);
            topicResult.setMobile(consumer.getMobile());

            listTopicResult.add(topicResult);
        }

        //3. 获取基础开奖数据
        SearchResult searchResult = lotteryDataService.searchLotteryData(token, 2016, 30, 0);
        List<LotteryDataResult> lotteryDatas = new LinkedList<LotteryDataResult>();

        for (LotteryData lotteryData :searchResult.getResult()) {

            BaseData baseData = baseDataService.getBaseDataByNumber(token, lotteryData.getNumber(),lotteryData.getYear());

            BaseData baseData1 = baseDataService.getBaseDataByNumber(token, lotteryData.getFlatNumber1(),lotteryData.getYear());
            BaseData baseData2 = baseDataService.getBaseDataByNumber(token, lotteryData.getFlatNumber2(),lotteryData.getYear());
            BaseData baseData3 = baseDataService.getBaseDataByNumber(token, lotteryData.getFlatNumber3(),lotteryData.getYear());
            BaseData baseData4 = baseDataService.getBaseDataByNumber(token, lotteryData.getFlatNumber4(),lotteryData.getYear());
            BaseData baseData5 = baseDataService.getBaseDataByNumber(token, lotteryData.getFlatNumber5(),lotteryData.getYear());
            BaseData baseData6 = baseDataService.getBaseDataByNumber(token, lotteryData.getFlatNumber6(),lotteryData.getYear());

            LotteryDataResult result = LotteryDataResult.fromLotteryDataResult(lotteryData);
            result.colorCode = baseData.getColorCode();
            result.zodiacCode = baseData.getZodiacCode();

            result.colorCode1 = baseData1.getColorCode();
            result.colorCode2 = baseData2.getColorCode();
            result.colorCode3 = baseData3.getColorCode();
            result.colorCode4 = baseData4.getColorCode();
            result.colorCode5 = baseData5.getColorCode();
            result.colorCode6 = baseData6.getColorCode();

            lotteryDatas.add(result);
        }
        
        Object object = session.getAttribute(Constant.SESSION_USER);

        if(object != null){
            model.addAttribute("consumer",object);
        }

        model.addAttribute("listResult",listResult);
        model.addAttribute("lotteryDatas",lotteryDatas);
        model.addAttribute("listTopicResult",listTopicResult);
        return "index";
    }

    @Autowired
    private ProductService productService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private LotteryDataService lotteryDataService;
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private OrderService orderService;



}
