package com.ab.test;

import java.util.Date;

import com.ab.util.DateUtil;

public class DateTest {

	public static void main(String[] args) {
		
		Date date = new Date();
		System.out.println(date);
		
		DateUtil.addHoursToJavaUtilDate(new Date(), 8384);
		System.out.println(DateUtil.getLastDays(7));
		
	}
	
}
