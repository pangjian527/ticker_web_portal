package com.tl.ticker.web.config;

import com.facebook.nifty.client.FramedClientChannel;
import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.nifty.client.NiftyClientChannel;
import com.facebook.swift.service.ThriftClientConfig;
import com.facebook.swift.service.ThriftClientManager;
import com.google.common.net.HostAndPort;
import com.google.common.util.concurrent.ListenableFuture;
import io.airlift.units.Duration;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by pangjian on 16-12-5.
 */
public class ProxyHandler implements InvocationHandler,Cloneable {

    private ServiceConfig config;
    private Class clazz;
    private long connectTimeoutMills = -1L;
    private InetSocketAddress address;

    public ProxyHandler(ServiceConfig config,Class clazz){
        this.config = config;
        this.clazz = clazz;
        address = new InetSocketAddress(this.config.host, this.config.port);
    }

    private  ThriftClientManager clientManager = new ThriftClientManager(); ;

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

            NiftyClientChannel channel = this.connect();
            Object target = clientManager.createClient(channel,this.clazz);

            return method.invoke(target,objects);
        }
    }

    private NiftyClientChannel connect() {
        try {
            FramedClientConnector e = new FramedClientConnector(this.address);
            ListenableFuture errMsg1 = this.clientManager.createChannel(e, ThriftClientConfig.DEFAULT_CONNECT_TIMEOUT, ThriftClientConfig.DEFAULT_RECEIVE_TIMEOUT, Duration.valueOf("30s"), ThriftClientConfig.DEFAULT_WRITE_TIMEOUT, 16777216, this.clientManager.getDefaultSocksProxy());
            FramedClientChannel framedClientChannel = null;
            if(this.connectTimeoutMills <= 0L) {
                framedClientChannel = (FramedClientChannel)errMsg1.get();
            } else {
                framedClientChannel = (FramedClientChannel)errMsg1.get(this.connectTimeoutMills, TimeUnit.MILLISECONDS);
            }

            return framedClientChannel;
        } catch (Exception var4) {
            String errMsg = "connect to address failed: " + this.address;
            return null;
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
