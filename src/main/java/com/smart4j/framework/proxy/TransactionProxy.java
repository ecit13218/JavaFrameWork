package com.smart4j.framework.proxy;

import com.smart4j.framework.annotation.Transaction;
import com.smart4j.use.util.DataBaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/25.
 */
public class TransactionProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
         try{   DataBaseHelper.beginTransaction();
            LOGGER.debug("begin transaction");
            result = proxyChain.doProxyChain();
            DataBaseHelper.commitTransaction();
            LOGGER.debug("commit transaction");}
            catch (Exception e){
             DataBaseHelper.rollbackTransaction();
             LOGGER.debug("rollback transaction");
             throw e;
            }
        }
        return null;
    }
}
