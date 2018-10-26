package com.william.utils;

import java.util.Date;

public class InformationUtils {
	public static Integer birthDayToAge(Integer birthDay) {
		Date date = new  Date();
		String format = FormatUtils.format(date);
		Integer nowDate = Integer.parseInt(format);
		System.out.println(nowDate);
		Integer ages = (nowDate - birthDay)/10000;
		
		return ages;
	}
	public static void main(String[] args) {
		System.out.println(birthDayToAge(19970212));	
	}
}
