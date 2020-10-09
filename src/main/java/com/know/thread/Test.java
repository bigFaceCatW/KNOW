package com.know.thread;

/**
 * @Author: FaceCat
 * @Date: 2020/10/1 16:50
 */
//线程的并发和异步
public class Test {


    public static void ric(int num) {
        try {
            int count=0;
            Thread.sleep(1000);
            if(num%2==0){
                count=2;
                System.out.println(Thread.currentThread().getName()+"偶数>>>"+count);
            }else{
                System.out.println(Thread.currentThread().getName()+"奇数>>>"+count);
                count=1;
            }
            System.out.println(count);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }






    }


    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int num = i;
            new Thread(() -> Test.ric(num)
            ).start();


        }
    }
}