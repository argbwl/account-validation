package com.ab.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AVLogger {
	
	private Logger logger;
	
	protected AVLogger(Logger logger) {
		this.logger=logger;
	}
	
	public static AVLogger getLogger(Class<?> classs) {
		AVLogger avLogger = new AVLogger(LoggerFactory.getLogger(classs));
		return avLogger;
	}
	
	public void info(String message, Object...values) {
		logger.info(message,values);
	}
	
	public void info(String message) {
		logger.info(message);
	}
	
	public void error(String message, Object...values) {
		logger.error(message,values);
	}
	
	public void error(String message) {
		logger.error(message);
	}
	
	public void warn(String message, Object...values) {
		logger.warn(message,values);
	}
	
	public void warn(String message) {
		logger.warn(message);
	}

}
