package com.know.thread.synchronize;

/**
 * @Author: Facecat
 * @Date: 2020/3/22 9:54
 */
//volatile关键字，保障多线程环境下保障可见性
public class volatileClass {
    //不加volatile线程会一直跑，不会停止，因为线程一直读本地内存，不会读主内存
    public volatile static boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
            }

        });
        t.start();
        Thread.sleep(1000);
        stop = true;

    }
}
