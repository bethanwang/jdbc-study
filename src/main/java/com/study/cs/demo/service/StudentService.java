package com.study.cs.demo.service;

import com.study.cs.demo.model.Student;

import java.util.List;

public interface StudentService {

    /**
     * 根据学生姓名查找学生列表
     * @return
     */
    List<Student> queryByName(String name);

    /**
     * 保存学员信息
     * @param stu
     * @return
     */
    void add(Student stu);

}
