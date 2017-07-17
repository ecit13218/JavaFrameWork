package com.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Created by Administrator on 2017/7/17.
 */
public class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     */
    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null)
                throw new Exception(fileName + "is not found");
            props=new Properties();
            props.load(is);
        } catch (Exception e) {
            LOGGER.error(fileName+"文件找不到",e);
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("关闭输入流失败");
                    e.printStackTrace();
                }
            }
        }
        return props;
    }
    public static String getString(Properties props,String key){
            return getString(props,key,"");
    }
    public static String getString(Properties props,String key,String defaultValue){
        String value=defaultValue;
        if (props.containsKey(key))
            value=props.getProperty(key);
        return value;
    }
    public static int getInt(Properties props,String key){
        return getInt(props, key, 0);
    }
    public static int getInt(Properties props,String key,int defaultValue){
        int value=defaultValue;
        if (props.containsKey(key))
            value = CastUtil.castInt(props.getProperty(key));
        return value;
    }
}
