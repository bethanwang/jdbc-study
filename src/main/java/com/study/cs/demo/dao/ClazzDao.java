package com.study.cs.demo.dao;


import com.study.cs.demo.model.Clazz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClazzDao extends BasicDao<Clazz> {

    public ClazzDao(Connection connection) {
        super(connection);
    }

    public Clazz save(Clazz c) throws Exception {
        //save clazz
        Connection conn = getConnection();
        String sql = "INSERT INTO clazz (clazz_name) VALUES (?)";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, c.getClazzName());
        ptmt.execute();
        //get clazz
        sql = "select * from clazz where clazz_name = ?";
        ptmt = conn.prepareCall(sql);
        ptmt.setString(1, c.getClazzName());
        ResultSet rs = ptmt.executeQuery();
        while(rs.next()){
            c.setClazzId(rs.getInt("clazz_id"));
        }
        return c;
    }

    public List<Clazz> queryByName(String clazzName) throws Exception {
        String sql = "select * from clazz where clazz_name = ? order by clazz_id";
        Connection conn = getConnection();
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, clazzName);
        ResultSet rs = ptmt.executeQuery();
        List<Clazz> list = new ArrayList<Clazz>();
        while(rs.next()){
            Clazz c = new Clazz();
            c.setClazzId(rs.getInt("clazz_id"));
            c.setClazzName(rs.getString("clazz_name"));
            list.add(c);
        }
        return list;
    }
}
