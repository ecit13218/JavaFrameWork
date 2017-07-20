package com.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * Created by Administrator on 2017/7/20.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
