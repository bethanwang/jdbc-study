package com.study.cs.demo.view;

import com.study.cs.demo.model.Clazz;
import com.study.cs.demo.model.Student;
import com.study.cs.demo.service.StudentService;
import com.study.cs.demo.service.impl.StudentServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Window extends JFrame {

    /**
     * Creates new form Window
     */
    public Window() {
        initComponents();
        this.setSize(815, 750);
        this.setLocationRelativeTo(null);
        //初始化table数据
        refreshTable(null);
    }

    //初始化窗口控件和布局
    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        inputName = new JTextField();
        jButton1 = new JButton();
        jPanel2 = new JPanel();
        stuNoLabel = new JLabel();
        nameLabel = new JLabel();
        classLabel = new JLabel();
        majorLabel = new JLabel();
        stuNoField = new JTextField();
        nameField = new JTextField();
        classField = new JTextField();
        majorField = new JTextField();
        resetBtn = new JButton();
        saveBtn = new JButton();
        delBtn = new JButton();
        exitBtn = new JButton();
        jScrollPane2 = new JScrollPane();
        table = new JTable();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("学员管理系统");

        jPanel1.setBorder(BorderFactory.createEtchedBorder());

        jLabel1.setText("输入学员姓名：");

        inputName.setName("inputNameField"); // NOI18N

        jButton1.setText("查找");
        jButton1.setActionCommand("");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputName, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(inputName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(BorderFactory.createEtchedBorder());

        stuNoLabel.setText("学号：");

        nameLabel.setText("姓名：");

        classLabel.setText("班级：");

        majorLabel.setText("专业：");

        resetBtn.setText("重置");
        resetBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetBtnMouseClicked(evt);
            }
        });

        saveBtn.setText("保存");
        saveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveBtnMouseClicked(evt);
            }
        });

        delBtn.setText("删除");
        delBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delBtnMouseClicked(evt);
            }
        });

        exitBtn.setText("退出");
        exitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitBtnMouseClicked(evt);
            }
        });

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(stuNoLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(stuNoField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(classLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(classField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(resetBtn)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(saveBtn)))))
                                .addGap(84, 84, 84)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(nameLabel)
                                                        .addComponent(majorLabel, GroupLayout.Alignment.TRAILING))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(nameField)
                                                        .addComponent(majorField, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(delBtn)
                                                .addGap(76, 76, 76)
                                                .addComponent(exitBtn)))
                                .addContainerGap(130, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(stuNoLabel)
                                        .addComponent(nameLabel)
                                        .addComponent(stuNoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(classLabel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(majorLabel)
                                        .addComponent(classField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(majorField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(resetBtn)
                                        .addComponent(saveBtn)
                                        .addComponent(delBtn)
                                        .addComponent(exitBtn))
                                .addGap(33, 33, 33))
        );

        table.setBorder(BorderFactory.createEtchedBorder());
        table.setModel(new DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "学号", "姓名", "班级", "专业"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(25);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2)
                                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(11, Short.MAX_VALUE))
        );

        pack();
    }

    //查找
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {
        String inputName = this.inputName.getText();
        //刷新table
        refreshTable(inputName);
    }

    //点击数据表某一行
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = this.table.getSelectedRow();
        String stuNo = this.table.getValueAt(row, 0).toString();
        String name = this.table.getValueAt(row, 1).toString();
        String stuClass = this.table.getValueAt(row, 2).toString();
        String major = this.table.getValueAt(row, 3).toString();
        this.stuNoField.setText(stuNo);
        this.stuNoField.setEditable(false);
        this.nameField.setText(name);
        this.classField.setText(stuClass);
        this.majorField.setText(major);
    }

    //重置
    private void resetBtnMouseClicked(java.awt.event.MouseEvent evt) {
        resetField();
    }

    //清空输入框的内容
    private void resetField(){
        this.stuNoField.setText("");
        this.nameField.setText("");
        this.classField.setText("");
        this.majorField.setText("");
        this.stuNoField.setEditable(true);
    }

    //保存
    private void saveBtnMouseClicked(java.awt.event.MouseEvent evt) {
        String stuNo = this.stuNoField.getText().toString();
        String name = this.nameField.getText().toString();
        String stuClass = this.classField.getText().toString();
        String major = this.majorField.getText().toString();
        Student s = new Student();
        if (stuNo.trim().length() > 0) {
            s.setStuNo(stuNo);
        }
        if (name.trim().length() > 0) {
            s.setName(name);
        }
        if (major.trim().length() > 0) {
            s.setMajor(major);
        }
        Clazz c = new Clazz();
        if (stuClass.trim().length() > 0) {
            c.setClazzName(stuClass);
        }
        s.setClazz(c);
        //保存学员信息
        stuService.add(s);
        resetField();
        refreshTable(null);
    }

    //删除
    private void delBtnMouseClicked(java.awt.event.MouseEvent evt) {
        String stuNo = this.stuNoField.getText().toString();
        String name = this.nameField.getText().toString();
        String stuClass = this.classField.getText().toString();
        String major = this.majorField.getText().toString();
        Student s = new Student();
        s.setStuNo(stuNo);
        s.setName(name);
        s.setMajor(major);
        Clazz c = new Clazz();
        c.setClazzName(stuClass);
        s.setClazz(c);
//        stuService.remove(s);
        //清空输入框内容
        resetField();
        //刷新table
        refreshTable(null);
    }

    //刷新数据表
    private void refreshTable(String name){
        List<Student> stuList = new ArrayList<Student>();
        //假数据
//        for(int i=1; i < 21; i++){
//            Student s = new Student();
//            s.setMajor("专业"+i);
//            s.setName("姓名"+i);
//            Clazz c = new Clazz();
//            c.setClazzName("班级"+i);
//            s.setClazz(c);
//            s.setStuNo("20160101100"+i);
//            stuList.add(s);
//        }

        try {
            stuList = stuService.queryByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //init table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while(model.getRowCount() > 0){
            model.removeRow(0);
        }

        stuList.forEach(s->{
            Vector data = new Vector();
            data.add(s.getStuNo());
            data.add(s.getName());
            data.add(s.getClazz().getClazzName());
            data.add(s.getMajor());
            model.addRow(data);
        });
    }

    //退出
    private void exitBtnMouseClicked(java.awt.event.MouseEvent evt) {
        this.dispose();
    }

    private StudentService stuService = new StudentServiceImpl();

//    private StudentDao studentDao = new StudentDao();

    private JTextField classField;
    private JLabel classLabel;
    private JButton delBtn;
    private JButton exitBtn;
    private JTextField inputName;
    private JButton jButton1;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane2;
    private JTextField majorField;
    private JLabel majorLabel;
    private JTextField nameField;
    private JLabel nameLabel;
    private JButton resetBtn;
    private JButton saveBtn;
    private JTextField stuNoField;
    private JLabel stuNoLabel;
    private JTable table;

}
