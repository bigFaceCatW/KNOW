package com.know.plan.mode.observer;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Facecat
 * @Date: 2020/9/30 14:22
 */
//车库(被观察者)
public class Garage {

    private List<Car> cars = new ArrayList<>();

    private int key;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
        notifyAllCar();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public  void notifyAllCar(){
            for(Car c:cars){
                c.launch(key);
            }

    }


}
