package com.know.base.plan.prin.rely;

/**
 * @Author: Facecat
 * @Date: 2020/9/17 21:37
 */
//依赖倒置，尽量使用接口来实现类的扩展
public class Test {
    public static void main(String[] args) {
/*=============================================接口注入*/
////        司机
//        Driver driver = new DriverSubclass();
////        奥迪车
//        Car car = new CarAudi();
//        driver.drive(car);
////        宝马车
//        Car carB= new CarAudi();
//        driver.drive(carB);

/*=============================================构造器注入*/
//        Car carA= new CarAudi();
//        Driver driver = new DriverSubclass(carA=);
//        driver.drive1();

/*=============================================set注入*/
        Car carB = new CarBwm();
        Driver driver = new DriverSubclass();
        driver.setCar(carB);
        driver.drive2();
    }
}
