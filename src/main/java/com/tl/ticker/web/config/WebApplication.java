package com.tl.ticker.web.config;

import com.tl.rpc.base.BaseDataService;
import com.tl.rpc.consumer.ConsumerService;
import com.tl.rpc.lottery.LotteryDataService;
import com.tl.rpc.msg.MsgService;
import com.tl.rpc.order.OrderService;
import com.tl.rpc.product.ProductService;
import com.tl.rpc.recharge.RechargeService;
import com.tl.rpc.reply.ReplyService;
import com.tl.rpc.sys.SysUserService;
import com.tl.rpc.topic.TopicService;
import com.tl.ticker.web.config.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by pangjian on 16-12-1.
 */
@Configuration
@PropertySource("classpath:server.conf")
public class WebApplication extends WebMvcConfigurerAdapter{

    @Value("${remote.server.host}")
    private String serverHost ;

    @Value("${remote.server.port}")
    private int serverPort;

    @Bean
    public ServiceConfig serviceConfig(){
        return new ServiceConfig(this.serverHost,this.serverPort);
    }

    @Bean
    public SecurityFilter securityFilter(){
        SecurityFilter filter = new SecurityFilter();
        return filter;
    }

    @Bean
    public ServiceProxy sysUserService() throws Exception{
        return new ServiceProxy(SysUserService.class,this.serviceConfig());
    }
    @Bean
    public ServiceProxy topicService() throws Exception{
        return new ServiceProxy(TopicService.class,this.serviceConfig());
    }
    @Bean
    public ServiceProxy consumerService() throws Exception{
        return new ServiceProxy(ConsumerService.class,this.serviceConfig());
    }
    @Bean
    public ServiceProxy baseDataService() throws Exception{
        return new ServiceProxy(BaseDataService.class,this.serviceConfig());
    }

    @Bean
    public ServiceProxy lotteryDataService() throws Exception{
        return new ServiceProxy(LotteryDataService.class,this.serviceConfig());
    }
    @Bean
    public ServiceProxy msgService() throws Exception{
        return new ServiceProxy(MsgService.class,this.serviceConfig());
    }
    @Bean
    public ServiceProxy orderService() throws Exception{
        return new ServiceProxy(OrderService.class,this.serviceConfig());
    }
    @Bean
    public ServiceProxy rechargeService() throws Exception{
        return new ServiceProxy(RechargeService.class,this.serviceConfig());
    }
    @Bean
    public ServiceProxy replyService() throws Exception{
        return new ServiceProxy(ReplyService.class,this.serviceConfig());
    }

    @Bean
    public ServiceProxy productService() throws Exception{
        return new ServiceProxy(ProductService.class,this.serviceConfig());
    }



}
