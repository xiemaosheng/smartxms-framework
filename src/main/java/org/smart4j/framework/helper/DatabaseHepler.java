package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库操作助手类
 * Created by xms on 2017/3/11 0011.
 */
public final class DatabaseHepler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHepler.class);

    private static final ThreadLocal<Connection> COLLECTION_THREAD_LOCAL = new ThreadLocal<Connection>();

    private static final String DRIVER;

    private static final String URL;

    private static final String USERNAME;

    private static final String PASSWORD;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            LOGGER.error("can not load jdbc driver",e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getCollection(){
        Connection conn = COLLECTION_THREAD_LOCAL.get();
        try {
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch (SQLException e){
            LOGGER.error("get connection failure",e);
            throw new RuntimeException(e);
        }finally {
            COLLECTION_THREAD_LOCAL.set(conn);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeCollection(){
        Connection conn = COLLECTION_THREAD_LOCAL.get();
        if (null != conn){
            try {
                conn.close();
            }catch (SQLException e){
                LOGGER.error("close connection failure",e);
                throw new RuntimeException(e);
            }finally {
                COLLECTION_THREAD_LOCAL.remove();
            }
        }
    }


    /**
     * 开启事务
     */
    public static void beginTransaction(){
        Connection conn =getCollection();
        if (null != conn){
            try {
                conn.setAutoCommit(false);
            }catch (SQLException e){
                LOGGER.error("begin transaction failure",e);
                throw new RuntimeException(e);
            }finally {
                COLLECTION_THREAD_LOCAL.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction(){
        Connection conn = getCollection();
        try {
            conn.commit();
            conn.close();
        }catch (SQLException e){
            LOGGER.error("commit transaction failure",e);
            throw new RuntimeException(e);
        }finally {
            COLLECTION_THREAD_LOCAL.remove();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction(){
        Connection conn = getCollection();
        try {
            conn.rollback();
            conn.close();
        }catch (SQLException e){
            LOGGER.error("rollback transaction failure",e);
            throw new RuntimeException(e);
        }finally {
            COLLECTION_THREAD_LOCAL.remove();
        }
    }
}
