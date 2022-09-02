package com.littlezhou.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//通知类

@Aspect
@Component  //需要将通知类也交给Spring管理
public class UserServiceAdvice {

    //定义切点
    @Pointcut("execution(void com.littlezhou.service.impl.*ServiceImpl.save(..))")
    private void pt(){}

    //增强功能的方法
    @Around("UserServiceAdvice.pt()")
    public Object method(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("执行之前。。。");
        //获取参数列表
        Object[] args = proceedingJoinPoint.getArgs();
        //执行方法
        //将异常抛出
        Object proceed = proceedingJoinPoint.proceed(args);

        System.out.println("执行后");
        //修改返回值...
        return  proceed;
    }


}
