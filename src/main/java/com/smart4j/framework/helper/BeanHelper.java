package com.smart4j.framework.helper;

import com.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean助手类，获取所有的实例，存放在map中
 * Created by Administrator on 2017/7/18.
 */
public class BeanHelper {
    private static final Map<Class<?>,Object> BEAN_MAP=new HashMap<>();
    static {
        Set<Class<?>> beanClassSet=ClassHelper.getBeanClassSet();
        for (Class<?> beanClass :
                beanClassSet) {
            Object obj= ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }

    /**
     * 获取MAP集合
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap()
    {
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     * @param clazz
     * @return
     */
    public static Object getBean(Class<?> clazz){
        if (!BEAN_MAP.containsKey(clazz)){
            throw new RuntimeException("无法找到对应的bean实例"+clazz);
        }
        return BEAN_MAP.get(clazz);
    }

}
