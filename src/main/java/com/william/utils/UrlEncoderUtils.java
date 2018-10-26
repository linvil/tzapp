package com.william.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncoderUtils {

	public static String encode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

}
