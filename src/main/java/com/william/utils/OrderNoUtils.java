package com.william.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNoUtils {

	private static Random random = new Random();

	private static DateFormat df = new SimpleDateFormat("yyMMddHHmmss");

	private static NumberFormat nf = new DecimalFormat("000");

	private static int count = 0;

	public static synchronized String getOrderNo() {
		if (count >= 1000) {
			count = 1;
		} else {
			count++;
		}

		String str = df.format(new Date()) + nf.format(count) + nf.format(random.nextInt(1000));

		return str;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(getOrderNo());
		}
	}

}
