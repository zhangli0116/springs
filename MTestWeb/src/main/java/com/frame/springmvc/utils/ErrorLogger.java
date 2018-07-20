package com.frame.springmvc.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author user
 * @Date 2018/7/20
 * @Time 12:10
 */
public class ErrorLogger {
    private static Logger log = LogManager.getLogger("error");
    private ErrorLogger(){}

    public static void log(Throwable t){
        log.error(t);
    }

    public static void log(String message){
        log.error(message);
    }
    public static void log(String message,Object...params){
        log.error(message, params);
    }
    public static void log(String message,Throwable t){
        log.error(message, t);;
    }
}
