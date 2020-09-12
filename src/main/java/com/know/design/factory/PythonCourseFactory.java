package com.know.design.factory;

/**
 * @Author: Facecat
 * @Date: 2020/3/22 16:19
 */
public class PythonCourseFactory implements CourseFactory {
    @Override
    public CourseClass createCourse() {
        return new PythonCourse();
    }
}
