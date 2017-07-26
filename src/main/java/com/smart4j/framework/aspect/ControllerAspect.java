package com.smart4j.framework.aspect;

import com.smart4j.framework.annotation.Aspect;
import com.smart4j.framework.annotation.Controller;
import com.smart4j.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 拦截Controller的所有方法
 * Created by Administrator on 2017/7/21.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    private static final Logger LOGGER= LoggerFactory.getLogger(ControllerAspect.class);
    private long begin;

    @Override
    public void before(Class<?> clazz, Method method, Object[] params) throws Throwable {
        LOGGER.debug("----------begin------------------");
        LOGGER.debug(String.format("class:%s",clazz.getName()));
        LOGGER.debug(String.format("Method:%s",method.getName()));
        begin=System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> clazz, Method method, Object[] params, Object result) throws Throwable {
        LOGGER.debug("----------after------------------");
        LOGGER.debug(String.format("class:%s",clazz.getName()));
        LOGGER.debug(String.format("Method:%s",method.getName()));
        LOGGER.debug(String.format("time:%dms",System.currentTimeMillis()-begin));
        LOGGER.debug("----------end------------------");
    }
}
