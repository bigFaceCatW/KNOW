package com.know.thread.synchronize;

/**互斥锁
 * @Author: Facecat
 * @Date: 2020/3/21 17:04
 */
public class SynchronizedClass  {
//作用范围（对象锁，类锁） 区别是否跨对象跨线程被保护


    public  synchronized  void method(){

    }

    public  void method1(){
        synchronized (this){ //当前调用这个方法的对象，对象级别

        }
    }

    public  synchronized  static void method2(){

    }

    public  void  method3(){
        synchronized (SynchronizedClass.class){ //生命周期最大，类级别

        }
    }

    public static void main(String[] args) {


    }
}
