package com.know.thread;

/**
 * @Author: FaceCat
 * @Date: 2020/10/1 16:50
 */
public class ThreadSingleton extends Thread {
    @Override
    public void run() {
        System.out.println("继承Thread");
    }

    public static void main(String[] args) {
        ThreadSingleton dto = new ThreadSingleton();
        dto.start();
    }
}
