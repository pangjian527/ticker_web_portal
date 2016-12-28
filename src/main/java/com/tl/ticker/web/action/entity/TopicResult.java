package com.tl.ticker.web.action.entity;

import com.tl.rpc.topic.Topic;

import java.util.Date;

/**
 * Created by pangjian on 16-12-3.
 */
public class TopicResult {

    public String title;
    public String content;
    public String mobile;
    public int readCount;
    public int replyCount;
    public String status;
    public String type;
    public String typeText;
    public int year;
    public int stage;
    public Date createTime;
    public String id;
    public String userId;

    public static TopicResult fromTopicResult(Topic topic){
        TopicResult result = new TopicResult();

        result.content = topic.getContent();
        result.id = topic.getId();
        result.createTime = new Date(topic.getCreateTime());
        result.stage = topic.getStage();
        result.year = topic.getYear();
        result.type = topic.getType().name();
        result.status = topic.getStatus().name();
        result.replyCount = topic.getReplyCount();
        result.readCount = topic.getReadCount();
        result.userId = topic.getUserId();
        result.title = topic.getTitle();

        return result;
    }

}
