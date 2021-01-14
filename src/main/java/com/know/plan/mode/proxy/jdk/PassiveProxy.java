package com.know.plan.mode.proxy.jdk;

/**
 * @Author: FaceCat
 * @Date: 2021/1/14 9:56
 */
public class PassiveProxy implements PassiveProxyInterface {


    @Override
    public String action(String param) {
        System.out.println("被代理类-代理方法入参>>>"+param);
        return "被代理类方法返回>>>"+param;
    }
}
