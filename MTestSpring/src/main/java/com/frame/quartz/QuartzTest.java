package com.frame.quartz;

import java.util.Date;

import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.DateBuilder.newDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


public class QuartzTest {
	/**
	 * 将调度Trigger和要调度的任务JobDetail分离
	 * Job被创建后，可以保存在Scheduler中，与Trigger是独立的，同一个Job可以有多个Trigger；这种松耦合的另一个好处是，当与Scheduler中的Job关联的trigger都过期时，可以配置Job稍后被重新调度，而不用重新定义Job；还有，可以修改或者替换Trigger，而不用重新定义与之关联的Job。
	 * @throws SchedulerException
	 * @throws InterruptedException
	 */

	@Test
	public void test1() throws SchedulerException, InterruptedException{
		//创建scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
       //定义一个Trigger
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1") //定义name/group
            .startNow()//一旦加入scheduler，立即生效
            .withSchedule(simpleSchedule() //使用SimpleTrigger
                .withIntervalInSeconds(1) //每隔一秒执行一次
                .repeatForever()) //一直执行，奔腾到老不停歇
            .build();
  
      //定义一个JobDetail
        JobDetail job = newJob(HelloJob.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
            .withIdentity("job1", "group1") //定义name/group
            .usingJobData("name", "quartz") //定义属性
            .build();
      //加入这个调度
        scheduler.scheduleJob(job, trigger);
        	
        //启动之
        scheduler.start();

        //运行一段时间后关闭
        Thread.sleep(15000);
        scheduler.shutdown(true);
		
	}
	
}
