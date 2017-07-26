package com.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
public class ProxyManager {
    public static <T> T createProxy(final Class<T> targetClass, final List<Proxy> proxyList) {
/**
 * 使用CGLib的Enhancer.create来创建代理对象，将intercept的参数传入构造器即可
 */
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList).doProxyChain();
            }
        });
    }
}
