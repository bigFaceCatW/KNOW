package com.know.plan.mode.adpater;

/**
 * @Author: Facecat
 * @Date: 2020/9/28 0:25
 */
public class ObjectMaster implements Servant {
    private Lancer lancerObject;

    public  ObjectMaster(Lancer lancer){
        super();
        this.lancerObject=lancer;
    }

    @Override
    public void remote() {
        lancerObject.remote();
    }

    @Override
    public void shortRange() {
        System.out.println("ObjectMaster");

    }
}
