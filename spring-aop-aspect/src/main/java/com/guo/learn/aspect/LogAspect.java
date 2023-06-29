package com.guo.learn.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
    // 在执行UserService的每个方法前执行:
    @Before("execution(public * com.guo.learn.service.UserService.*(..))")
    public void doAccessCheck() {
        System.err.println("[Before] do access check...");
    }

    @Around("execution(public * com.guo.learn.service.MailService.*(..))")
    public Object doLogging(ProceedingJoinPoint pjp)throws Throwable{
        System.err.println("[Around] start " + pjp.getSignature());
        Object retVal = pjp.proceed();
        System.err.println("[Around] done " + pjp.getSignature());
        System.out.println(retVal);
        return retVal;
    }

}
