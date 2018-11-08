package com.test.mspringboot.aspect;

import com.test.mspringboot.annotation.TimeLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Create by Administrator on 2018/11/8
 * 计时服务类方法执行时间
 * @author admin
 */
@Component
@Aspect
@Order(2)
public class TimeLoggerAspectJ {
    private Logger logger = LoggerFactory.getLogger(TimeLoggerAspectJ.class);
    @Pointcut(value="@within(org.springframework.stereotype.Service) && @annotation(timeLogger)")
    public void pointCutTime(TimeLogger timeLogger){}

    @Around(value = "pointCutTime(timeLogger)")
    public Object printTimeLogger(ProceedingJoinPoint pjp, TimeLogger timeLogger) throws Throwable{
        Long start = System.currentTimeMillis();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        //当前对象
        Object target = pjp.getTarget();
        //当前执行类的方法
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        //执行代理方法
        Object obj = pjp.proceed();
        Long end = System.currentTimeMillis();
        logger.info("############# {}.{} : {} ms#############",target.getClass().getName(),currentMethod.getName(),end - start);
        return obj;
    }
}
