package com.know.base.plan.mode.adpater;

/**
 * @Author: Facecat
 * @Date: 2020/9/28 0:22
 */
//适配器模式
public class Test {
    public static void main(String[] args) {
//       类适配器
        Servant t1 = new Master();
        t1.remote();
        t1.shortRange();

//       对象适配器
        Servant t2 = new ObjectMaster(new Lancer());
        t2.remote();
        t2.shortRange();

//       接口适配器
        Servant t3 = new InterfaceMaster();
        t3.remote();
        t3.shortRange();

    }
}
