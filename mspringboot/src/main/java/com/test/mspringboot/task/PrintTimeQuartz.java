package com.test.mspringboot.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by Administrator on 2018/10/30
 *
 * @author admin
 */
@Service("printTimeQuartz")
public class PrintTimeQuartz implements Serializable {
    private Logger logger = LoggerFactory.getLogger(PrintTimeQuartz.class);
    /**
     *
     */
    private static final long serialVersionUID = -2057846704016861663L;

    public void execute() {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("Quartz PrintTimeQuartz.execute()... : Current time is :"+simpleDateFormat.format(new Date()));
    }
}
