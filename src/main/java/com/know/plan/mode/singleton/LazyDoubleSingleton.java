package com.know.plan.mode.singleton;

/**
 * @Author: Facecat
 * @Date: 2020/9/24 22:08
 */
//双重检查

public class LazyDoubleSingleton {
//    缺点：加上volatile防止指令重排序
    private volatile static LazyDoubleSingleton doubleCheck;

    private LazyDoubleSingleton(){
    }
    public  static LazyDoubleSingleton getInstance(){
//        1、检查是否要阻塞
        if(doubleCheck==null) {
            synchronized (LazyDoubleSingleton.class) {
//        2、检查是否要创建实例
                if (doubleCheck == null) {
                    doubleCheck = new LazyDoubleSingleton(); //缺点：指令重排序问题
                }
            }
        }
        return doubleCheck;
    }
}
