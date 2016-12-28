package com.tl.ticker.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by pangjian on 16-11-30.
 */
@EnableAutoConfiguration
@SpringBootApplication
public class TickerWebServer {

    public static void main(String[] args ){
        SpringApplication.run(TickerWebServer.class,args);
    }

}
