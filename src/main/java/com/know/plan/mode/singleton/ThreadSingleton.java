package com.know.plan.mode.singleton;

/**
 * @Author: Facecat
 * @Date: 2020/9/25 22:20
 */
public class ThreadSingleton {

    private static final ThreadLocal<ThreadSingleton> threadLocal=
            new ThreadLocal<ThreadSingleton>(){
                @Override
                protected ThreadSingleton initialValue() {
                    return new ThreadSingleton();
                }
            };

    private ThreadSingleton(){

    }

    public static ThreadSingleton getInstance(){
        return threadLocal.get();
    }
}
