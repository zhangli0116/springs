package com.frame.springmvc.aspect;

import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class TimeAspect {
	
	@Pointcut("execution(* com..*.service..*.*(..))")
	public void doAccessCheck(){}
	
	@Around("doAccessCheck()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		long start = System.currentTimeMillis();
		Object retVal = pjp.proceed();
		long end = System.currentTimeMillis();  
		System.out.println("Task1 finished ,time elapsed: " +  (end-start) + " ms");
		return retVal;
	}
}
