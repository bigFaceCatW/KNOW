package com.know.base.plan.mode.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: FaceCat
 * @Date: 2021/1/14 10:18
 */
//jdk动态代理
public class MyInvocationHandler implements InvocationHandler {
    Object obj;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method为被代理类的目标方法,方法调用时会触发！！！
        Object returnVal = method.invoke(obj, args);//调用被代理类的action方法
        return returnVal;
    }

//    实例化被代理对象，返回一个代理类对象
    public Object blind(Object obj){
        this.obj=obj;
//    返回一个代理类对象
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
    }

    public static void main(String[] args) {
        PassiveProxy passiveProxy = new PassiveProxy();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler();
        Object object= myInvocationHandler.blind(passiveProxy);
        PassiveProxyInterface passiveProxyInterface = (PassiveProxyInterface) object;
        String str=passiveProxyInterface.action("jdk代理");
    }

}
