package com.frame.springmvc.task.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.frame.springmvc.utils.DebugLogger;


public class MyDetailQuartzJobBean extends QuartzJobBean{
	
	/**
	 * 目标对象
	 */
	private String targetObject;
	/**
	 * 执行的方法
	 */
    private String targetMethod;
    /**
     * spring 上下文对象	
     */
    private ApplicationContext ctx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		 try {
			DebugLogger.log("execute [" + targetObject + "] at once>>>>>>");
			Object otargetObject = ctx.getBean(targetObject);
			Method m = otargetObject.getClass().getMethod(targetMethod, new Class[] {});
			m.invoke(otargetObject, new Object[]{});
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.ctx = applicationContext;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

}
