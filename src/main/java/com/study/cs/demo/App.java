package com.study.cs.demo;

import com.study.cs.demo.view.Window;

public class App {

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window().setVisible(true);
            }
        });
    }

}
