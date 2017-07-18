package com.smart4j.framework.helper;

import com.smart4j.framework.annotation.Inject;
import com.smart4j.framework.util.CollectionUtil;
import com.smart4j.framework.util.ReflectionUtil;
import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * Created by Administrator on 2017/7/18.
 */
public final class IocHelper {
    static {
        Map<Class<?>,Object> beanMap=BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)){
            //遍历map集合
            for (Map.Entry<Class<?>, Object> beanEntry:beanMap.entrySet()){
            Class<?> beanClass=beanEntry.getKey();
            Object beanInstance=beanEntry.getValue();
            Field[] beanFields=beanClass.getDeclaredFields();//获取所有成员变量
                if(ArrayUtils.isNotEmpty(beanFields)){
                    //遍历成员变量数组
                    for(Field beanField:beanFields){
                       if(beanField.isAnnotationPresent(Inject.class)){//当注解写的是注入时
                           Class<?> beanFieldClass=beanField.getType();//得到该类的具体对象
                           Object beanFieldInstance=beanMap.get(beanFieldClass);//获得对应的Object
                           if(beanFieldInstance!=null){
                               ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);//通过反射设置对应的初始化的值
                           }
                       }
                    }
                }

            }
        }
    }
}
