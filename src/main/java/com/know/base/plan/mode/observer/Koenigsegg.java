package com.know.base.plan.mode.observer;

/**
 * @Author: FaceCat
 * @Date: 2020/9/30 14:47
 */
//观察者
public class Koenigsegg extends Car {

    public Koenigsegg (Garage garage){
        this.garage = garage;
        this.garage.addCar(this);
    }

    @Override
    void launch(int key) {
            System.out.println("柯尼塞格>>"+key);
    }
}
