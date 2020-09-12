package com.know.design.factory;

/**
 * @Author: Facecat
 * @Date: 2020/3/22 16:16
 */
public class PythonCourse implements CourseClass {

    @Override
    public void create() {
        System.out.println("python学习");
    }
}
