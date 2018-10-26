package com.william.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Months;

public class DateUtils {

	/**
	 * 计算日期间隔月数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int monthsBetween(Date start, Date end) {
		Months months = Months.monthsBetween(new DateTime(start), new DateTime(end));

		return months.getMonths();
	}

	/**
	 * 计算日期间隔分钟数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int minutesBetween(Date start, Date end) {
		Minutes minutes = Minutes.minutesBetween(new DateTime(start), new DateTime(end));

		return minutes.getMinutes();
	}

	public static Date addDays(Date date, int amount) {
		return org.apache.commons.lang.time.DateUtils.addDays(date, amount);
	}

	public static Date addMinutes(Date date, int amount) {
		return new DateTime(date).plusMinutes(amount).toDate();
	}

	public static Date addSeconds(Date date, int amount) {
		return new DateTime(date).plusSeconds(amount).toDate();
	}

	public static Date getWeek(Date date) {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		currentDate.set(GregorianCalendar.HOUR_OF_DAY, 0);
		currentDate.set(GregorianCalendar.MINUTE, 0);
		currentDate.set(GregorianCalendar.SECOND, 0);
		currentDate.set(GregorianCalendar.MILLISECOND, 0);
		return currentDate.getTime();
	}

	public static long date2Timestamp(Date date) {
		return date.getTime() / 1000;
	}

	public static Date timestamp2Date(long timestamp) {
		return new Date(timestamp * 1000);
	}

	public static void main(String[] args) {
		System.out.println(getWeek(null));
	}
	
	public static Date getAfterDay(Date date, Integer amount){
		return new Date(DateUtils.addDays(date, amount).getTime());
	}
	
	public static List<Integer> removeDuplicateId(List<Integer> list){
		List<Integer> unique = list.stream().distinct().collect(Collectors.toList());
		return unique;
	}
}
