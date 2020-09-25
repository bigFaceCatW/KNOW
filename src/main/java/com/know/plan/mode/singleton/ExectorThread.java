package com.know.plan.mode.singleton;

/**
 * @Author: Facecat
 * @Date: 2020/9/24 21:43
 */
public class ExectorThread implements  Runnable {

    public void text(){
        System.out.println("测试");
    }
    @Override
    public void run() {

//        LazySingleton lazySingleton = LazySingleton.getInstance();

//        LazyDoubleSingleton lazySingleton = LazyDoubleSingleton.getInstance();

        ThreadSingleton lazySingleton = ThreadSingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+">>>"+lazySingleton);

    }
}
