package com.study.cs.demo.dao;

import com.study.cs.demo.db.DBUtil;
import com.study.cs.demo.model.Clazz;
import com.study.cs.demo.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDao extends BasicDao<Student> {

    public StudentDao(Connection connection) {
        super(connection);
    }

    public List<Student> query(String name) throws Exception {
        Connection conn = DBUtil.getInstance().getConnection();
        String sql = "select s.*,c.clazz_name from student s left join clazz c on s.clazz_id=c.clazz_id";
        if(name != null && !name.trim().equals("")){
            sql = sql + " where s.`name` like ?";
        }
        PreparedStatement ptmt = conn.prepareStatement(sql);
        if(name != null && !name.trim().equals("")){
            ptmt.setString(1, name);
        }
        ResultSet rs = ptmt.executeQuery();
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
        return list;
    }

    /**
     * select by stu_no, if exist update student info, else insert student
     * @param s
     * @return
     * @throws Exception
     */
    public void save(Student s) throws Exception {
        Connection conn = getConnection();
        //select by stuNo
        Student oldStu = queryByStuNo(s.getStuNo());
        //if exist update
        if(oldStu != null){
            update(s);
        } else {//else insert
            String sql = "insert into student (stu_no,name,major,clazz_id) values (?,?,?,?)";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, s.getStuNo());
            ptmt.setString(2, s.getName());
            ptmt.setString(3, s.getMajor());
            ptmt.setInt(4, s.getClazz().getClazzId());
            ptmt.execute();
        }
    }

    public void update(Student stu) throws Exception {
        Connection conn = getConnection();
        String sql = "update student set name=?,major=?,clazz_id=? where stu_no=?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, stu.getName());
        ptmt.setString(2, stu.getMajor());
        ptmt.setInt(3, stu.getClazz().getClazzId());
        ptmt.setString(4, stu.getStuNo());
        ptmt.execute();
    }

    public Student queryByStuNo(String stuNo) throws Exception {
        if(stuNo == null || stuNo.trim().equals("")){
            return null;
        }
        Connection conn = getConnection();
        String sql = "select s.*,c.clazz_name from student s left join clazz c on s.clazz_id=c.clazz_id where s.stu_no=?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, stuNo);
        ResultSet rs = ptmt.executeQuery();
        Student s = null;
        while(rs.next()){
            s = new Student();
            s.setMajor(rs.getString("major"));
            s.setName(rs.getString("name"));
            s.setStuNo(rs.getString("stu_no"));
            Clazz c = new Clazz();
            c.setClazzId(rs.getInt("clazz_id"));
            c.setClazzName(rs.getString("clazz_name"));
            s.setClazz(c);
        }
        return s;
    }

}
