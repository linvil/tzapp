package com.william.utils;

import java.util.Map;

/**
 * 字典工具类
 * 
 * @author zpl
 *
 */
public class CodeUtils {

	private static Map<String, String> dicJsonMap;
	private static Map<String, Map<String, String>> dicMap;

	public static void setDicJsonMap(Map<String, String> dicJsonMap) {
		CodeUtils.dicJsonMap = dicJsonMap;
	}

	public static void setDicMap(Map<String, Map<String, String>> dicMap) {
		CodeUtils.dicMap = dicMap;
	}

	/**
	 * 转换为字典名称
	 * 
	 * @param codeType
	 * @param code
	 * @return
	 */
	public static String codeToName(String codeType, String code) {
		Map<String, String> map = dicMap.get(codeType);

		if (map == null) {
			return code;
		}

		String name = map.get(code);

		return name == null ? code : name;
	}

	/**
	 * 获取字典JSON字符串
	 * 
	 * @param codeType
	 * @return
	 */
	public static String getDicJson(String codeType) {
		return dicJsonMap.get(codeType);
	}

	/**
	 * 检验代码值是否合法
	 * 
	 * @param codeType
	 * @param code
	 * @return
	 */
	public static boolean checkCode(String codeType, String code) {
		Map<String, String> map = dicMap.get(codeType);

		if (map == null) {
			return false;
		}

		return map.containsKey(code);
	}

}
