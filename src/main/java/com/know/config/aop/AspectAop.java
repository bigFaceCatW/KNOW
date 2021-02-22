package com.know.config.aop;

import com.know.config.annotation.optLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: FaceCat
 * @Date: 2021/1/16 14:16
 */
@Aspect
@Component
//@Aspect 注解用来描述一个切面类，定义切面类的时候需要打上这个注解。
// @Component 注解将该类交给 Spring 来管理
public class AspectAop {
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
//    private void postPointcut(){
//
//    }

//    @Pointcut(value = "@annotation(com.noob.annotation.OpLog)")用于独立的注解，argNames3
//    public void methodPointcut(){}

    @Pointcut(value = "@annotation(accessLog)", argNames = "accessLog")

    private void logPointcut(optLog accessLog) {

    }
    //表示controller包下所有以controller结尾的类的所有方法
    @Pointcut("execution(* com.know.mp.controller.*Controller.*(..))")
//    第一个 * 号的位置：表示返回值类型，* 表示所有类型。
//    包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，
//    在本例中指 com.mutest.controller包、子包下所有类的方法。
//    第二个 * 号的位置：表示类名，* 表示所有类。
//    *(..)：这个星号表示方法名，* 表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
    public void beforeLogPoint() {
    }


    @Before("beforeLogPoint()")
    public void before(JoinPoint joinPoint) {
        // 获取签名
        Signature signature = joinPoint.getSignature();
        // 获取切入的包名
        String declaringTypeName = signature.getDeclaringTypeName();
        // 获取即将执行的方法名
        String funcName = signature.getName();
        // 也可以用来记录一些信息，比如获取请求的 URL 和 IP
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求 URL
        String url = request.getRequestURL().toString();
        // 获取请求 IP
        String ip = request.getRemoteAddr();
        Object[] objects = joinPoint.getArgs(); //获取入参
        System.out.println("前置通知");
    }

    // Before表示logAdvice将在目标方法执行前执行
    @Around(value = "logPointcut(accessLog)")
    public Object around(ProceedingJoinPoint joinPoint, optLog accessLog) {
        // 这里只是一个示例，你可以写任何处理逻辑
        System.out.println("环绕通知前");
        Object ret = new Object();
        try {
            //获取请求参数，详见接口类
            Object[] objects = joinPoint.getArgs();
             ret = joinPoint.proceed(); //方法无返回值ret为null
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("环绕通知后");
      return ret;

    }
}