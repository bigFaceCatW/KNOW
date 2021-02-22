package com.know.base.plan.prin.replacement;

import java.util.HashMap;

/**
 * @Author: Facecat
 * @Date: 2020/9/17 22:38
 */
public class Test {
    public static void main(String[] args) {
//        当子类的方法重载父类的方法时，方法的前置条件（即方法的形参）要比父类方法的输入参数更宽松。永远走父类，不会改变！！
//        下面结果不通 1走父类,2走子类
        Gun akGun1 = new AkGun();
        HashMap hashMap1 = new HashMap();
        akGun1.mainTain(hashMap1);

        AkGun akGun2 = new AkGun();
        HashMap hashMap2= new HashMap();
        akGun2.mainTain(hashMap2);

    }
}
