package com.know.plan.mode.singleton;

/**
 * @Author: Facecat
 * @Date: 2020/9/24 21:36
 */
//优点：节省了内存 （每次使用才new对象）
//缺点1：线程不安全  （可能存在2个线程同时调用getInstance方法，导致前一个还没返回、后者已改变了值）
//缺点2：:加上synchronized线程安全的、但资源只能一个人访问，性能低

public class LazySingleton {
    private static LazySingleton lazySingleton;

    private  LazySingleton(){
    }
//加上synchronized是线程安全的1线程进来后，2线程会在外面阻塞，等1出去再进来
    public synchronized static LazySingleton getInstance(){
        if(lazySingleton==null) {
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
