package com.test.mspringboot.task;

import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Create by Administrator on 2018/10/30
 *
 * @author admin
 */
@Component
public class MySchedulerFactoryBean implements SchedulerFactoryBeanCustomizer {
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setOverwriteExistingJobs(true);//更新trigger的 表达式时，同步数据到数据库qrtz_cron_triggers表 开启
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");//注入applicationContext

    }
}
