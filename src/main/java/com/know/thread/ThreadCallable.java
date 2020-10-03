package com.know.thread;

import java.util.concurrent.*;

/**
 * @Author: FaceCat
 * @Date: 2020/10/1 16:51
 */
public class ThreadCallable implements Callable<String> {
    @Override
    public String call(){
        System.out.println("Callable进入");
        return "success";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadCallable threadCallable = new ThreadCallable();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<String> future=executorService.submit(threadCallable);
        System.out.println(future.get()); //通过阻塞

    }
}
