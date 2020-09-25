package com.know.plan.mode.singleton;

/**
 * @Author: Facecat
 * @Date: 2020/9/24 21:42
 */

//右击断点，可以进行线程调试
//    内存中只有一个实例、减少内存消耗

public class Test {
    public static void main(String[] args) {
//        Thread t1 = new Thread(new ExectorThread());
//        Thread t2 = new Thread(new ExectorThread());
//        t1.start();
//        t2.start();
//        System.out.println("end");
//        2.通过发射获取类
//        Class<?> clazz=LazySingleton.class;
//        try {
//            Constructor constructor=clazz.getDeclaredConstructor();
//            constructor.setAccessible(true);
//            Object object=constructor.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        System.out.println(ThreadSingleton.getInstance());
        System.out.println(ThreadSingleton.getInstance());
        System.out.println(ThreadSingleton.getInstance());

        Thread t1 = new Thread(new ExectorThread());
        Thread t2 = new Thread(new ExectorThread());
        t1.start();
        t2.start();

        
        }
    }

