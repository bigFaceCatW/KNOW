package com.know.plan.mode.proxy.cg;


import com.know.plan.mode.proxy.jdk.PassiveProxy;
import com.know.plan.mode.proxy.jdk.PassiveProxyInterface;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: FaceCat
 * @Date: 2021/1/14 11:21
 */
public class CgProxy implements MethodInterceptor {

    public Object getInstance(Class<?> cls){

        Enhancer Enhancer = new Enhancer();//创建代理类
        Enhancer.setSuperclass(cls); //创建父类
        Enhancer.setCallback(this); //设置回调
        return Enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = methodProxy.invokeSuper(o,objects);
        return result;
    }

    public static void main(String[] args) {
        CgProxy cgProxy = new CgProxy();
        PassiveProxyInterface passiveProxyInterface= (PassiveProxyInterface) cgProxy.getInstance(PassiveProxy.class);
        passiveProxyInterface.action("cg代理");
    }
}
