package com.know.thread.synchronize;

/**
 * @Author: Facecat
 * @Date: 2020/3/22 9:54
 */
public class volatileClass {
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
