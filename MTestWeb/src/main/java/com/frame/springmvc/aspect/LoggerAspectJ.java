package com.frame.springmvc.aspect;

import com.frame.springmvc.annotation.ExceptionLogger;
import com.frame.springmvc.annotation.TimeLogger;
import com.frame.springmvc.utils.DebugLogger;
import com.frame.springmvc.utils.ErrorLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author user
 * @Date 2018/7/20
 * @Time 12:04
 * 通过注解来控制logger
 */

@Aspect
@Component
public class LoggerAspectJ {
    /**
     * 时间执行log
     * @param timeLogger
     */
    //@annotation：用于匹配当前执行方法持有指定注解的方法
    //@within(注解类型全限定名)匹配所有持有指定注解的类里面的方法, 即要把注解加在类上. 在接口上声明不起作用
    //@Pointcut(value="@within(org.springframework.stereotype.Service) && @annotation(com.learn.frame.spring.annotation.TimeLogger)")
    @Pointcut(value="@within(org.springframework.stereotype.Service) && @annotation(timeLogger)")
    public void pointCutTime(TimeLogger timeLogger){}


    /**
     *aop通知参数传递
     */

    @Around(value = "pointCutTime(timeLogger)")
    public Object printTimeLogger(ProceedingJoinPoint pjp, TimeLogger timeLogger) throws Throwable{
        Long start = System.currentTimeMillis();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod(); //获得是接口的方法
        Object target = pjp.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());//当前执行类的方法
        TimeLogger timeLogger2 = currentMethod.getAnnotation(TimeLogger.class); //获得注释
        Object obj = pjp.proceed();
        Long end = System.currentTimeMillis();
        DebugLogger.log("################{}.{} : {} ms ##############" ,target.getClass().getName(),currentMethod.getName(),(end-start));
        //DebugLogger.log("###########{}",timelogger.value());
        return obj;
    }

    /**
     * 异常log
     * @param exceptionLogger
     */
    @Pointcut(value="@annotation(exceptionLogger)")
    public void pointCutExcption (ExceptionLogger exceptionLogger){}

    /**
     * 配置抛出异常后通知
     * @param exceptionLogger
     * @param e
     */
    @AfterThrowing(value="pointCutExcption(exceptionLogger)",throwing="e")
    public void printExceptionLogger(ExceptionLogger exceptionLogger,Exception e){
        ErrorLogger.log("#############{}#############" ,exceptionLogger.value());
        ErrorLogger.log("###################异常####",e);
    }
}
