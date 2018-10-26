package com.william.utils;

import java.math.BigDecimal;
import java.nio.charset.Charset;

public class Constants {

	public static final String DEFAULT_CHARSET_NAME = "UTF-8";

	public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);

	public static final BigDecimal TAX_RATE = new BigDecimal("0.05");

	public static final String DEFAULT_IMAGE_URL = "/upload/nopic.png";

	public static final String WX_APP_NAME = "gzh";
	public static boolean isConn = false;
}
