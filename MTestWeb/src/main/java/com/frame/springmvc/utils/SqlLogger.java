package com.frame.springmvc.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author user
 * @Date 2018/7/20
 * @Time 14:43
 */
public class SqlLogger {
    private static Logger log = LogManager.getLogger("sql");
    private SqlLogger(){}
    public static void log(String message){
        log.debug(message);
    }
}
