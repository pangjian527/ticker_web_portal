package com.tl.ticker.web.config;

/**
 * Created by pangjian on 16-12-2.
 */
public class ServiceConfig {

    public String host;

    public int port;

    public ServiceConfig(){}

    public ServiceConfig(String host,int port){
        this.host = host;
        this.port = port;
    }
}
