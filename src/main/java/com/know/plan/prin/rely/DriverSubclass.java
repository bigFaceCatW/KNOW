package com.know.plan.prin.rely;

/**
 * @Author: Facecat
 * @Date: 2020/9/17 21:31
 */
public class DriverSubclass implements Driver {
    private Car car;

//    接口依赖倒置
    @Override
    public void drive(Car car) {
        car.run();
    }
    public DriverSubclass() {

    }

//    构造器依赖倒置
    public DriverSubclass(Car car) {
       this.car=car;
    }

    @Override
    public void  drive1(){
        this.car.run();
    }


//    set方法注入
    public Car getCar() {
        return car;
    }

    @Override
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public void  drive2(){
        this.car.run();
    }

}
