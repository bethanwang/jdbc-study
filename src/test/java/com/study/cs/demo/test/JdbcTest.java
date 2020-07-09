package com.study.cs.demo.test;

import com.study.cs.demo.model.Clazz;
import com.study.cs.demo.model.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcTest {

    //驱动类
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    //数据库位置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cs-demo?useSSL=false&characterEncoding=utf8";
    //登陆数据库用户名
    private static final String USER_NAME = "root";
    //登陆数据库密码
    private static final String PASSWORD = "123456";

    public static void main(String[] a) {
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try {
            //1 加载驱动
            /**
             * 通过反射机制，生成驱动类对象，并加载驱动类对象
             */
            //Class.forName(DRIVER_CLASS);
            //2 打开链接
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            //3 创建会话
            sta = conn.createStatement();
            //4 执行sql
            /**
             * 特别注意：执行完sql需要释放数据库连接资源
             */
            String sql = "select s.*,c.clazz_name from student s left join clazz c on s.clazz_id=c.clazz_id";
            rs = sta.executeQuery(sql);
            //5 处理sql执行结果
            List<Student> list = new ArrayList<Student>();
            while(rs.next()){
                Student s = new Student();
                s.setMajor(rs.getString("major"));
                s.setName(rs.getString("name"));
                s.setStuNo(rs.getString("stu_no"));
                Clazz c = new Clazz();
                c.setClazzId(rs.getInt("clazz_id"));
                c.setClazzName(rs.getString("clazz_name"));
                s.setClazz(c);
                list.add(s);
            }
            //遍历从数据库中查询的对象集合
            list.forEach(s->{
                System.out.println(s.toString());
            });
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            /**
             * 无论程序执行到哪个位置出现了异常，都需要释放数据库连接资源
             * 所以，释放资源的代码放在finally块中执行
             */

            /*
            conn/sta/set都有可能是null，所以在使用这几个对象之前先判断是否是null
            */
            if(conn != null){
                try {
                    conn.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(sta != null){
                try {
                    sta.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(rs != null){
                try {
                    rs.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

}
