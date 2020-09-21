package com.know.plan.prin.replacement;

import java.util.HashMap;

/**
 * @Author: Facecat
 * @Date: 2020/9/17 22:22
 */
public class AkGun extends Gun {

//1.子类可以实现父类的抽象方法，但不能覆盖父类的非抽象方法。
    public long mainTain(HashMap hashMap) {
        System.out.println("子类重载父类方法");
        return 0;
    }
}
