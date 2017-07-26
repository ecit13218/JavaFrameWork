package com.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * 切面代理
 * Created by Administrator on 2017/7/21.
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> clazz = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        begin();
        try {
            if (interept(clazz, method, params)) {
                before(clazz, method, params);
                result = proxyChain.doProxyChain();
                after(clazz,method,params,result);
            }
        } catch (Throwable throwable) {
           logger.error("代理失败",throwable);
           error(clazz,method,params,throwable);
           throw throwable;
        }finally {
            end();
        }
        return result;
    }

    public void begin() {
    }

    private boolean interept(Class<?> clazz, Method method, Object[] params)throws Throwable {
        return true;
    }

    public void before(Class<?> clazz, Method method, Object[] params)throws Throwable {
    }

    public void after(Class<?> clazz, Method method, Object[] params, Object result)throws Throwable {
    }

    public void error(Class<?> clazz, Method method, Object[] params, Throwable e)throws Throwable {
    }

    public void end()throws Throwable {
    }
}
