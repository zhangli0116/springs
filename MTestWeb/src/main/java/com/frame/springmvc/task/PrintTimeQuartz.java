package com.frame.springmvc.task;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Service;

import com.frame.springmvc.utils.DebugLogger;

/**
 * 一个时间打印任务 Quartz集群实现
 * job 实现类需要需要序列化 集群使用
 */
@Service("printTimeQuartz")
public class PrintTimeQuartz implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2057846704016861663L;
	
	public void execute() {
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DebugLogger.log("Quartz PrintTimeQuartz.execute()... : Current time is :"+simpleDateFormat.format(new Date()));

	}

}
