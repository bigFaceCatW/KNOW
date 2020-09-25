package com.know.plan.mode.singleton;

/**
 * @Author: Facecat
 * @Date: 2020/9/24 21:42
 */

//右击断点，可以进行线程调试
public class Test {
    public static void main(String[] args) {
        Thread t1 = new Thread(new ExectorThread());
        Thread t2 = new Thread(new ExectorThread());
        t1.start();
        t2.start();
        System.out.println("end");
    }

}
