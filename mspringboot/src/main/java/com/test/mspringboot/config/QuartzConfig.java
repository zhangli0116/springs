package com.test.mspringboot.config;

import com.test.mspringboot.task.utils.MyDetailQuartzJobBean;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by Administrator on 2018/10/29
 *
 * @author admin
 */
@Configuration
public class QuartzConfig {


    /**
     * 定义JobDetail 直接使用 jobDetailFactoryBean.getObject 获得的是空
     * @return
     */
    @Bean
    public JobDetailFactoryBean printTimeJobDetail(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        // durability 表示任务完成之后是否依然保留到数据库，默认falses
        jobDetailFactoryBean.setDurability(true);
        //当Quartz服务被中止后，再次启动或集群中其他机器接手任务时会尝试恢复执行之前未完成的所有任务
        jobDetailFactoryBean.setRequestsRecovery(true);
        jobDetailFactoryBean.setJobClass(MyDetailQuartzJobBean.class);
        jobDetailFactoryBean.setDescription("打印时间定时器2");
        Map<String,String> jobDataAsMap = new HashMap<>();
        jobDataAsMap.put("targetObject","printTimeQuartz"); //spring 中bean的名字
        jobDataAsMap.put("targetMethod","execute");   //执行方法名
        jobDetailFactoryBean.setJobDataAsMap(jobDataAsMap);
        return  jobDetailFactoryBean;
    }

    /**
     * 定义一个Trigger
     * @return
     */
    @Bean
    public CronTriggerFactoryBean printTimeCronTrigger(JobDetail printTimeJobDetail){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        // 设置jobDetail
        cronTriggerFactoryBean.setJobDetail(printTimeJobDetail);
        //秒 分 小时 日 月 星期 年  每10分钟
        cronTriggerFactoryBean.setCronExpression("0 0/10 * * * ?");
        //trigger超时处理策略 默认1：总是会执行头一次 2:不处理
        cronTriggerFactoryBean.setMisfireInstruction(2);
        return  cronTriggerFactoryBean;
    }


}
