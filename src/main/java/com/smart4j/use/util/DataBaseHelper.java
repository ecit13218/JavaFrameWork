package com.smart4j.use.util;

import com.smart4j.framework.helper.ConfigHelper;
import com.smart4j.framework.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/7/17.
 */
public class DataBaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseHelper.class);
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static final ThreadLocal<Connection> CONNECTION_HOLDER=new ThreadLocal<>();

    static {
        DRIVER = ConfigHelper.getJdbcDriver();
        URL = ConfigHelper.getJdbcURL();
        USERNAME = ConfigHelper.getJdbcUserName();
        PASSWORD = ConfigHelper.getJdbcPassword();
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("初始化出错");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("获取数据库连接失败", e);
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("关闭数据库连接失败", e);
                e.printStackTrace();
            }
        }
    }

    public static void beginTransaction() {
        Connection conn = getConnection();
        try {
            if (conn != null) {
                conn.setAutoCommit(false);
            }
        } catch (SQLException e) {
           LOGGER.error("打开事务失败",e);
            throw new RuntimeException(e);
        }finally {
            CONNECTION_HOLDER.set(conn);
        }
    }
    public static void commitTransaction(){
        Connection conn = getConnection();
        try {
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            LOGGER.error("提交事务失败",e);
            throw new RuntimeException(e);
        }finally {
            CONNECTION_HOLDER.remove();
        }
    }
    public static void rollbackTransaction(){
        Connection conn=getConnection();
        try {
            conn.rollback();
            conn.close();
        } catch (SQLException e) {
            LOGGER.error("回滚事务失败",e);
            throw new RuntimeException(e);
        }finally {
            CONNECTION_HOLDER.remove();
        }
    }
}
