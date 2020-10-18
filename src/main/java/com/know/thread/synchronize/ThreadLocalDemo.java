package com.know.thread.synchronize;

/**
 * @Author: FaceCat
 * @Date: 2020/10/5 10:36
 */
public class ThreadLocalDemo {
//如果不隔离每次获取会往上递增5、10、15
    private static int num =0;
//每次获取的num初始值都是0,达到线程隔离效果(线程级别隔离)
//   static ThreadLocal <Integer> local = new ThreadLocal<Integer>(){
//        @Override
//        protected  Integer initialValue(){
//            return 0;
//        }
//    };

    public static void main(String[] args) {
        Thread[] threads = new Thread[5];

        for(int i=0;i<5;i++){
            threads[i] = new Thread(() -> {
//                int num = local.get(); //获取初始值
//                local.set(num+=5);//设置local中的值
                num += 5;
                System.out.println(Thread.currentThread().getName()+">>>"+num);
            });
        }
        for(int s=0;s<5;s++){
            threads[s].start();
        }

    }
}
