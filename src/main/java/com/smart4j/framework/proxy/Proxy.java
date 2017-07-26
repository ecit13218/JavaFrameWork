package com.smart4j.framework.proxy;

/**
 * 代理接口
 * Created by Administrator on 2017/7/20.
 */
public interface Proxy {
    /**
     * 执行链式代理(就是一个一个执行下去的代理)
     * @return
     */
    Object doProxy(ProxyChain proxyChain ) throws Throwable;
}
