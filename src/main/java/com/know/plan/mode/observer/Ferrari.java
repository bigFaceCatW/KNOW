package com.know.plan.mode.observer;

/**
 * @Author: FaceCat
 * @Date: 2020/9/30 14:44
 */
//观察者
public class Ferrari extends Car {

    public Ferrari (Garage garage){
        this.garage=garage;
        this.garage.addCar(this);
    }
    @Override
    void launch(int key) {
        System.out.println("法拉利>>>"+key);

    }
}
