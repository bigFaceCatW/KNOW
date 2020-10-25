package com.know.thread.synchronize;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Facecat
 * @Date: 2020/3/22 10:51
 */
public class LockClass {

    private static int i = 0;

//    重入锁
    static Lock mLock = new ReentrantLock();

    private static void increase() {
        mLock.lock();//互斥锁
        try {
            Thread.sleep(1);
            i++;
            System.out.println(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            mLock.unlock();
        }
    }


    public static void main(String[] args) {


        for(int j=0;j<1000;j++){
            new Thread(()->LockClass.increase()).start();
        }
    }



}
