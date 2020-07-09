package com.study.cs.demo.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.util.List;

public abstract class BasicDao<T> {

    /**
     * 查询
     * @param sql
     * @param params
     * @param clazz
     * @return
     * @throws Exception
     */
    public List<T> query(String sql, Object[] params, Class<T> clazz) throws Exception {
        QueryRunner runner = new QueryRunner();
        List<T> list = runner.query(conn, sql, new BeanListHandler<T>(clazz), params);
        return list;
    }

    /**
     * 更新数据库，写操作，包含：update, insert, delete
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    public boolean update(String sql, Object[] params) throws Exception {
        QueryRunner runner = new QueryRunner();
        int cou = runner.update(conn, sql, params);
        if (cou > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 批量更新，批量对数据库写操作
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    public boolean updateBatch(String sql, Object[][] params) throws Exception {
        QueryRunner runner = new QueryRunner();
        int cou = runner.batch(sql, params).length;
        if (cou > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Connection getConnection(){
        return this.conn;
    }

    public BasicDao(Connection connection){
        this.conn = connection;
    }

    private Connection conn;

}
