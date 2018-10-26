package com.william.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.william.exception.FormatException;

public class FormatUtils {

	public static final int DATE_FORMAT_YYYYMMDD = 1;

	public static final int DATE_FORMAT_YYYY_MM_DD = 2;

	public static final int DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = 3;

	private static Map<Integer, DateFormat> dateFormatMap;

	static {
		dateFormatMap = new HashMap<>();

		dateFormatMap.put(DATE_FORMAT_YYYYMMDD, new SimpleDateFormat("yyyyMMdd"));
		dateFormatMap.put(DATE_FORMAT_YYYY_MM_DD, new SimpleDateFormat("yyyy-MM-dd"));
		dateFormatMap.put(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	public static String format(Date date) {
		return format(date, DATE_FORMAT_YYYYMMDD);
	}

	public static String format(Date date, int dateFormat) {
		DateFormat formatter = dateFormatMap.get(dateFormat);

		if (formatter == null) {
			throw new FormatException("日期格式错误");
		}

		return formatter.format(date);
	}

	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, DATE_FORMAT_YYYYMMDD);
	}

	public static Date parseDate(String dateStr, int dateFormat) {
		DateFormat formatter = dateFormatMap.get(dateFormat);

		if (formatter == null) {
			throw new FormatException("日期格式错误");
		}

		try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			throw new FormatException("转换日期格式出错");
		}
	}

}
