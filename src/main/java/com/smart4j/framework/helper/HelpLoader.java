package com.smart4j.framework.helper;

import com.smart4j.framework.annotation.Controller;
import com.smart4j.framework.util.ClassUtil;

/**
 * 加载类，为了让初始化更加的集中
 * Created by Administrator on 2017/7/19.
 */
public class HelpLoader {
    public static void init(){
        Class<?>[] classList=new Class[]{ClassHelper.class,BeanHelper.class,IocHelper.class, Controller.class};
        for (Class clazz :
                classList) {
            ClassUtil.loadClass(clazz.getName(), false);
        }
    }
}
