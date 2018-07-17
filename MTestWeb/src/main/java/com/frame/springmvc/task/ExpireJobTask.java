package com.frame.springmvc.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service("expireJobTask")
public class ExpireJobTask {
	
	//整合quartz
	public void run(){
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Quartz ExpireJobTask.run()... : Current time is :"+simpleDateFormat.format(new Date()));
	}
	
	//spring 内部定时器 每分钟第6秒触发
	/*@Scheduled(cron="06 0/1 * * * ?")
	public void reportCurrentTime(){
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Spring ExpireJobTask.run()... : Current time is :"+simpleDateFormat.format(new Date()));
	}*/
}