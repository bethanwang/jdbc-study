package com.study.cs.demo.model;

import lombok.Data;

@Data
public class Student {
    private String stuNo;
    private String name;
    private String major;
    private Clazz clazz;
}
