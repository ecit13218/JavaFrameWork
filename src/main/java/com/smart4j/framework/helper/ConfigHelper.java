package com.smart4j.framework.helper;

import com.smart4j.framework.ConfigConstant;
import com.smart4j.framework.util.PropsUtil;

import java.util.Properties;

/**
 * Created by Administrator on 2017/7/17.
 */
public class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_DRIVER);
    }
    public static String getJdbcURL(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_URL);
    }
    public static String getJdbcUserName(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_USERNAME);
    }
    public static String getJdbcPassword(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_PASSWORD);
    }
    public static String getAppBasePackge(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_BASE_PACKGE,"smart.framework.app.base_package");
    }
    public static String getAppJspPath(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_JSP_PATH,"smart.framework.app.jsp_path");
    }
    public static String getAppAssetPath(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_ASSET_PATH,"smart.framework.app.asset_path");
    }
}
