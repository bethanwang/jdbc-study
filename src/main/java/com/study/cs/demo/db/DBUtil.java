package com.study.cs.demo.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {

    /**
     * 返回数据库连接池
     * @return
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * 返回单例对象
     * @return
     */
    public static DBUtil getInstance(){
        if(util == null){
            synchronized (new Object()){
                if (util == null){
                    util = new DBUtil();
                }
            }
        }
        return util;
    }

    /**
     * 构造器，从properties中加载配置信息，并初始化数据库连接
     */
    private DBUtil(){
        try {
            //获得properties文件的位置
//            String proFilePath = new File("").getCanonicalPath() + "/src/main/resources/db.properties";
//            InputStream is = new FileInputStream(proFilePath);
            InputStream is = DBUtil.class.getResourceAsStream("/db.properties");
            Properties pro = new Properties();
            pro.load(is);
            DRIVER_CLASS = pro.getProperty("driver-class");
            DB_URL = pro.getProperty("db-url");
            USER_NAME = pro.getProperty("user-name");
            PASSWORD = pro.getProperty("password");
            Class.forName(DRIVER_CLASS);
            //根据properties中的配置，创建数据库连接
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DBUtil util;
    //数据库连接
    private Connection conn;
    //配置驱动类
    private static String DRIVER_CLASS;
    //数据库位置
    private static String DB_URL;
    //登陆数据库用户名
    private static String USER_NAME;
    //登陆数据库密码
    private static String PASSWORD;

}
