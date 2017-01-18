package com.tl.ticker.web.action;

import com.tl.rpc.common.ServiceToken;
import com.tl.rpc.consumer.Consumer;
import com.tl.rpc.consumer.ConsumerService;
import com.tl.rpc.reply.REPLYSTATUS;
import com.tl.rpc.reply.Reply;
import com.tl.rpc.reply.ReplyService;
import com.tl.rpc.reply.SearchReplyResult;
import com.tl.rpc.topic.*;
import com.tl.ticker.web.action.entity.PageResult;
import com.tl.ticker.web.action.entity.ReplyResult;
import com.tl.ticker.web.action.entity.ResultJson;
import com.tl.ticker.web.action.entity.TopicResult;
import com.tl.ticker.web.common.Constant;
import com.tl.ticker.web.util.JsonUtil;
import com.tl.ticker.web.util.StrFunUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pangjian on 16-12-22.
 */
@Controller
public class TopicAction {

    @RequestMapping("/portal/topic/view")
    public String view(Model model, String id, HttpServletRequest request) throws Exception{

        ServiceToken token = new ServiceToken();
        Topic topic = topicService.getByTopicId(new ServiceToken(), id);

        topic.setReadCount(topic.getReadCount()+1);
        topicService.saveTopic(token,topic);

        Consumer consumer = consumerService.getById(token, topic.getUserId());
        TopicResult topicResult = TopicResult.fromTopicResult(topic);
        topicResult.setMobile(consumer.getMobile());

        int offset = StrFunUtil.valueInt(request.getParameter("offset"),0);
        int limit = StrFunUtil.valueInt(request.getParameter("limit"),15);

        SearchReplyResult searchReplyResult = replyService.searchReplyByTopicId(token, limit, offset, id);

        List<ReplyResult>  replyResults = new LinkedList<ReplyResult>();

        for (Reply reply : searchReplyResult.getResult()) {

            ReplyResult replyResult = ReplyResult.fromReply(reply);
            Consumer repConsumer = consumerService.getById(token, reply.getUserId());
            replyResult.setMobile(repConsumer.getMobile());
            replyResults.add(replyResult);
        }

        String url = "/portal/topic/view?id="+id;
        model.addAttribute("pageResult",new PageResult(searchReplyResult.getTotalCount(),limit,offset,url));
        model.addAttribute("topic",topicResult);
        model.addAttribute("replyResults",replyResults);

        return "topic/view";
    }


    @ResponseBody
    @RequestMapping("/portal/topic/reply")
    public String reply(Model model, HttpSession session, String topicId, String content) throws Exception{

        Object object = session.getAttribute(Constant.SESSION_USER);

        if(object == null){
            return JsonUtil.toString(new ResultJson(false,"请先登录！"));
        }

        Consumer consumer = (Consumer)object;

        Reply reply = new Reply();
        reply.setStatus(REPLYSTATUS.OPEN);
        reply.setUserId(consumer.getId());
        reply.setTopicId(topicId);
        reply.setContent(content);
        reply.setCreateTime(new Date().getTime());

        ServiceToken token = new ServiceToken();
        replyService.saveReply(token,reply);

        Topic topic = topicService.getByTopicId(token, topicId);
        topic.setReplyCount(topic.getReadCount()+1);
        topic.setUpdateTime(new Date().getTime());

        topicService.saveTopic(token,topic);

        return JsonUtil.toString(new ResultJson(true));
    }

    @RequestMapping("/portal/topic/deliver")
    public String deliver(Model model,HttpSession session){

        Object object = session.getAttribute(Constant.SESSION_USER);

        if(object == null){
            return "redirect:/portal/login";
        }

        return "topic/deliver";
    }


    @RequestMapping("/portal/topic/save")
    public String save(Model model,String title,String content,HttpSession session) throws Exception{


        Object object = session.getAttribute(Constant.SESSION_USER);

        if(object == null){
            return "redirect:/portal/login";
        }

        Consumer consumer = (Consumer)object;

        ServiceToken token = new ServiceToken();

        Topic topic = new Topic();
        topic.setCreateTime(new Date().getTime());
        topic.setUpdateTime(new Date().getTime());
        topic.setType(TOPICTYPE.CHARGE);
        topic.setStatus(TOPICSTATUS.OPEN);
        topic.setContent(content);
        topic.setTitle(title);
        topic.setReplyCount(0);
        topic.setReadCount(0);
        topic.setUserId(consumer.getId());
        topic.setUpdateTime(new Date().getTime());

        topicService.saveTopic(token,topic);

        return "redirect:/portal/topic/search";
    }

    @RequestMapping("/portal/topic/search")
    public String search(Model model,HttpServletRequest request) throws Exception{
        int offset = StrFunUtil.valueInt(request.getParameter("offset"),0);
        int limit = StrFunUtil.valueInt(request.getParameter("limit"),50);
        String mobile = request.getParameter("mobile") ==null ?"":request.getParameter("mobile");

        ServiceToken token = new ServiceToken();
        SearchTopicResult topicResultList = topicService.searchTopic(token, limit, offset, TOPICSTATUS.OPEN,mobile);

        List<TopicResult> listTopicResult = new LinkedList<TopicResult>();
        for (Topic topic : topicResultList.getResult()) {

            Consumer consumer = consumerService.getById(token, topic.getUserId());
            TopicResult topicResult = TopicResult.fromTopicResult(topic);
            topicResult.setMobile(consumer.getMobile());

            listTopicResult.add(topicResult);
        }

        String url = "/portal/topic/search?mobile="+mobile;
        model.addAttribute("pageResult",new PageResult(topicResultList.getTotalCount(),limit,offset,url));
        model.addAttribute("listTopicResult",listTopicResult);
        model.addAttribute("mobile",mobile);
        return "topic/topic_list";
    }


    @Autowired
    private TopicService topicService;
    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private ReplyService replyService;
}
