package com.test.mspringboot.task.utils;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Create by Administrator on 2018/10/29
 *
 * @author admin
 */
public class MyDetailQuartzJobBean extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(MyDetailQuartzJobBean.class);
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
            logger.info("execute [" + targetObject + "] at once>>>>>>");
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
