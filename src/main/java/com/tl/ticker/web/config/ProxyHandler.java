package com.tl.ticker.web.config;

import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.swift.service.ThriftClientManager;
import com.google.common.net.HostAndPort;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by pangjian on 16-12-5.
 */
public class ProxyHandler implements InvocationHandler,Cloneable {

    private ServiceConfig config;
    private Class clazz;

    public ProxyHandler(ServiceConfig config,Class clazz){
        this.config = config;
        this.clazz = clazz;
    }

    private final ThriftClientManager clientManager = new ThriftClientManager();

    public Object invoke(Object o, Method method, Object[] objects) throws Exception {
        if(method.getDeclaringClass() == Object.class) {
            String var4 = method.getName();
            byte var5 = -1;
            switch(var4.hashCode()) {
                case -1776922004:
                    if(var4.equals("toString")) {
                        var5 = 0;
                    }
                    break;
                case -1295482945:
                    if(var4.equals("equals")) {
                        var5 = 1;
                    }
                    break;
                case 147696667:
                    if(var4.equals("hashCode")) {
                        var5 = 2;
                    }
            }

            switch(var5) {
                case 0:
                    return this.clazz.toString();
                case 1:
                    return Boolean.valueOf(this.equals(Proxy.getInvocationHandler(objects[0])));
                case 2:
                    return Integer.valueOf(this.hashCode());
                default:
                    throw new UnsupportedOperationException();
            }
        } else {
            HostAndPort hostAndPort =
                    HostAndPort.fromParts(this.config.host, this.config.port);

            Object target = clientManager.createClient(new FramedClientConnector(hostAndPort),
                    this.clazz).get();

            return method.invoke(target,objects);
        }
    }

    public void close() throws IOException {
        closeQuietly(this.clientManager);
    }

    protected static void closeQuietly(Closeable closeable) {
        if(null != closeable) {
            try {
                closeable.close();
            } catch (IOException var2) {
                ;
            }
        }
    }
}
