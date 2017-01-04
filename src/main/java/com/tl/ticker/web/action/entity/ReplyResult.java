package com.tl.ticker.web.action.entity;

import com.tl.rpc.reply.Reply;

import java.util.Date;

/**
 * Created by pangjian on 16-12-23.
 */
public class ReplyResult {

    public String id;

    public String content;

    public String userId;

    private String mobile;

    public Date createTime;

    public String status;

    public String topicId;

    public static ReplyResult fromReply(Reply reply){
        ReplyResult replyResult = new ReplyResult();

        replyResult.id = reply.getId();
        replyResult.content = reply.getContent();
        replyResult.createTime = new Date(reply.getCreateTime());
        replyResult.userId = reply.getUserId();
        replyResult.topicId = reply.getTopicId();
        replyResult.status = reply.getStatus().name();

        return replyResult;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        String preMobile = this.mobile.substring(0, 3);
        String nextMobile = this.mobile.substring(7);

        return preMobile+"****"+nextMobile;
    }

}
