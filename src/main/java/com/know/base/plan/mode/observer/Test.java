package com.know.base.plan.mode.observer;

/**
 * @Author: FaceCat
 * @Date: 2020/9/30 14:52
 */
//观察者模式
public class Test
{
    public static void main(String[] args) {
        Garage dto = new Garage();
        new Ferrari(dto);
        new Koenigsegg(dto);
        dto.setKey(1);
    }
}
