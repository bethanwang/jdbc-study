package com.study.cs.demo.service.impl;

import com.study.cs.demo.dao.ClazzDao;
import com.study.cs.demo.dao.StudentDao;
import com.study.cs.demo.db.DBUtil;
import com.study.cs.demo.model.Clazz;
import com.study.cs.demo.model.Student;
import com.study.cs.demo.service.StudentService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    @Override
    public List<Student> queryByName(String name) {
        List<Student> list = new ArrayList<Student>();
        try {
            list = stuDao.query(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void add(Student stu) {
        try{
            //设置手动处理事务
            conn.setAutoCommit(false);
            //clazz bp
            List<Clazz> clazzlist = clazzDao.queryByName(stu.getClazz().getClazzName());
            Clazz c;
            if(clazzlist != null && clazzlist.size() > 0){
                c = clazzlist.get(0);
            }else{
                //save clazz
                c = clazzDao.save(stu.getClazz());
            }
            //student bp
            stu.setClazz(c);
            stuDao.save(stu);
            //提交事务
            conn.commit();
        }catch(Exception e){
            try {
                //回滚事务
                conn.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public StudentServiceImpl() {
        conn = DBUtil.getInstance().getConnection();
        stuDao = new StudentDao(conn);
        clazzDao = new ClazzDao(conn);
    }

    private StudentDao stuDao;
    private ClazzDao clazzDao;
    private Connection conn;
}
