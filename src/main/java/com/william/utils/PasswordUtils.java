package com.william.utils;

public class PasswordUtils {

	/**
	 * 生成盐值
	 * 
	 * @return
	 */
	public static String getSaltValue() {
		return MathUtils.getRandomString(10);
	}

	/**
	 * 加密
	 * 
	 * @param password
	 * @param saltValue
	 * @return
	 */
	public static String encrypt(String password, String saltValue) {
		if (saltValue != null) {
			password = password + saltValue;
		}

		return HashUtils.getMd5(password.getBytes());
	}

}
