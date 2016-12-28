package com.tl.ticker.web.config;

import com.facebook.swift.service.ThriftClientManager;
import org.springframework.beans.factory.FactoryBean;

import java.io.Closeable;
import java.lang.reflect.Proxy;

/**
 * Created by pangjian on 16-12-2.
 */
public class ServiceProxy<T> implements FactoryBean{

    private final ThriftClientManager clientManager = new ThriftClientManager();

    private Class<T> clazz;
    private ServiceConfig serviceConfig;

    public ServiceProxy(Class<T> clazz,ServiceConfig serviceConfig){
        this.clazz = clazz;
        this.serviceConfig = serviceConfig;
    }
    public Object getObject() throws Exception {

        ProxyHandler handler = new ProxyHandler(serviceConfig,clazz);

       return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz,Closeable.class},handler));
    }

    public Class<?> getObjectType() {
        return this.clazz;
    }


    public boolean isSingleton() {
        return false;
    }
}
