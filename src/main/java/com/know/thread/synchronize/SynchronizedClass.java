package com.know.thread.synchronize;

/**互斥锁
 * @Author: Facecat
 * @Date: 2020/3/21 17:04
 */
public class SynchronizedClass  {



    //    public  synchronized  static void method2(){
//
//    }
    //静态方法、类对象 (类锁)
    public  void  method3(){
        synchronized (SynchronizedClass.class){

        }
    }

    //当前调用这个方法的对象(实例锁)
    public  void method1(){
        synchronized (this){

        }
    }

    public  synchronized  void method(){ //相当于synchronized(this)

    }




    public static void main(String[] args) {


    }
}
