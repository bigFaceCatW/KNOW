package com.know.base.plan.mode.adpater;

/**
 * @Author: Facecat
 * @Date: 2020/9/28 0:19
 */
public class Master extends Lancer implements Servant {
    @Override
    public void shortRange() {
        System.out.println("宿主技能");
    }
}
