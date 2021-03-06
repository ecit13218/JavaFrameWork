package com.smart4j.framework.helper;

import com.smart4j.framework.annotation.Controller;
import com.smart4j.framework.annotation.Service;
import com.smart4j.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 获取Bean工具类
 * Created by Administrator on 2017/7/18.
 */
public final class ClassHelper {
    /**
     * 定义类集合
     */
    private static final Set<Class<?>>CLASS_SET;

    static {
        String basePackge=ConfigHelper.getAppBasePackge();//获取配置文件里的package，等同于读取spring配置文件里的扫描包路径
        CLASS_SET= ClassUtil.getClassSet(basePackge);//获取所有的类
    }

    /**
     * 获得包下的所有类
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取所有Service注解的类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet=new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if(clazz.isAnnotationPresent(Service.class))
            {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取所有Controller注解的类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet=new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if(clazz.isAnnotationPresent(Controller.class))
            {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取所有带有Service和Controller的类
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet=new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    /**
     * 获取应用包名下某父类或者接口的所有子类，CGLib代理的原理就是生成一个子类覆盖父类里面的方法进行增强
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet=new HashSet<>();
        for (Class<?> clazz:CLASS_SET
             ) {
            if (superClass.isAssignableFrom(clazz)&&!superClass.equals(clazz)){
                classSet.add(clazz);
            }
        } return classSet;
    }

    /**
     * 获取应用包名下所有带有某注解的类
     */
    public static Set<Class<?>> getClassByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz:
             CLASS_SET) {
            if (clazz.isAnnotationPresent(annotationClass)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }
}
