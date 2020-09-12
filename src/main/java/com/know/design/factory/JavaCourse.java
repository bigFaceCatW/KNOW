package com.know.design.factory;

/**
 * @Author: Facecat
 * @Date: 2020/3/22 16:16
 */
public class JavaCourse implements CourseClass {
    @Override
    public void create() {
        System.out.println("java学习");
    }
}
