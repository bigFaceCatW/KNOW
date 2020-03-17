package com.know.info.controller;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Facecat
 * @Date: 2020/3/16 15:49
 */
public class ThreadController implements Runnable {
    @Override
    public void run() {
        for(int i=0; i<20; i++){
            System.out.println(Thread.currentThread().getName()+":"+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadController thread = new ThreadController();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5,10,2000,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>());
        for(int i=0;i<5;i++){
           Future future =  pool.submit(thread);
           try{
              Object object = future.get();
           System.out.println(JSON.toJSONString(object));
           }catch(Exception e){
           e.printStackTrace();
           }
        }
        pool.shutdown();//关闭线程

    }

}