package com.frame.springmvc.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugLogger {
	private static Logger log = LogManager.getLogger("debug");
	private DebugLogger(){}
	public static void log(String message){
		log.debug(message);
	}
	public static void log(String message,Object...params){
		log.debug(message, params);
	}
}
