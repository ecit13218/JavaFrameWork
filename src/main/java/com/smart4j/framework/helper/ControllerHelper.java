package com.smart4j.framework.helper;

import com.smart4j.framework.annotation.Action;
import com.smart4j.framework.bean.Handler;
import com.smart4j.framework.bean.Request;
import com.smart4j.framework.util.CollectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import javax.sound.midi.ControllerEventListener;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Controller助手类
 * Created by Administrator on 2017/7/18.
 */
public class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            //遍历controller类
            for (Class<?> controllerClass :
                    controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();//获取类里面的方法
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method :
                            methods) {
                        //遍历方法并判断方法是否有Action注解
                        if (method.isAnnotationPresent(Action.class)) {
                            //从Action注解中获取URL的映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证URL映射规则
                            if (mapping.matches("\\w+:/\\w*")) ;
                            {
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    //获取请求方法与请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }

            }
        }
    }
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
