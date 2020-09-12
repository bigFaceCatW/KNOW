package com.know.design.factory;

/**
 * @Author: Facecat
 * @Date: 2020/3/22 16:24
 */
public class FactoryTest {

    public static void main(String[] args) {

        CourseFactory courseFactory = new JavaCourseFactory();
        CourseClass course =courseFactory.createCourse();
        course.create();
    }
}
