package com.know.base.plan.mode.singleton;

/**
 * @Author: Facecat
 * @Date: 2020/9/24 21:32
 */
//优点：执行效率搞、性能高、没有任何锁
//缺点：内存浪费（静态会每次都创建类）
public class HungrySingleton {
//    先静态、后动态
//    先上后下
//    先属性后方法
    private static final HungrySingleton hungrySingleton = new HungrySingleton();

    private HungrySingleton(){

    }
    private  static HungrySingleton getInstance(){
        return  hungrySingleton;
    }
}
