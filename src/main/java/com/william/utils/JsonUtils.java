package com.william.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.william.exception.JsonException;

public class JsonUtils {
	/**
	 * 空对象的的JSON字符串默认值: '{}'.
	 */
	public static final String EMPTY_JSON = "{}";

	protected static String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";
	protected static SerializeConfig mapping = new SerializeConfig();
	/*
	 * QuoteFieldNames———-输出key时是否使用双引号,默认为true
	 * WriteMapNullValue——–是否输出值为null的字段,默认为false
	 * WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
	 * WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
	 * WriteNullStringAsEmpty—字符类型字段如果为null,输出为"",而非null
	 * WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
	 */
	protected static SerializerFeature[] features = { SerializerFeature.DisableCircularReferenceDetect,
			SerializerFeature.WriteMapNullValue, SerializerFeature.SortField, SerializerFeature.WriteDateUseDateFormat,
			// SerializerFeature.WriteNullStringAsEmpty,
			SerializerFeature.WriteNullListAsEmpty };
	
	public static SerializerFeature[] featuresStringAsEmpty = { SerializerFeature.DisableCircularReferenceDetect,
		SerializerFeature.WriteMapNullValue, SerializerFeature.SortField, SerializerFeature.WriteDateUseDateFormat,
		SerializerFeature.WriteNullStringAsEmpty,
		SerializerFeature.WriteNullListAsEmpty };
	
	static {
		defaultDateFormat = "yyyy-MM-dd HH:mm:ss";
		mapping.put(Date.class, new SimpleDateFormatSerializer(defaultDateFormat));
	}
	public static String formatJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new JsonException("格式化JSON出错", e);
		}
	}

	public static String formatJson(Object obj, String[] filterNames, String[][] propertyNames) {
		ObjectMapper mapper = new ObjectMapper();

		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		for (int i = 0; i < filterNames.length; i++) {
			filterProvider.addFilter(filterNames[i], SimpleBeanPropertyFilter.filterOutAllExcept(propertyNames[i]));
		}

		mapper.setFilters(filterProvider);

		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new JsonException("格式化JSON出错", e);
		}
	}

	public static String formatJsonIgnoreProperties(Object obj, String[] filterNames, String[][] propertyNames) {
		ObjectMapper mapper = new ObjectMapper();

		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		for (int i = 0; i < filterNames.length; i++) {
			filterProvider.addFilter(filterNames[i], SimpleBeanPropertyFilter.serializeAllExcept(propertyNames[i]));
		}

		mapper.setFilters(filterProvider);

		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new JsonException("格式化JSON出错", e);
		}
	}

	public static Map<String, String> jsonObject2StringMap(JSONObject json) {
		if (json == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();
		for (String key : json.keySet()) {
			map.put(key, json.getString(key));
		}

		return map;
	}
	/**
	 * 将对象实例obj转为json格式字符串;<br/>
	 * 当null==obj时, 则返回 null.
	 * 
	 * @param obj
	 *            对象实例.
	 * @return String json格式字符串.
	 */
	public static String toJson(Object obj) {
		if (null == obj) {
			return null;
		}
		return JSON.toJSONString(obj, mapping, features);
	}
	
	public static String toJson(Object obj,SerializerFeature[] features) {
		if (null == obj) {
			return null;
		}
		return JSON.toJSONString(obj, mapping, features);
	}

	/**
	 * 将json格式字符串转为Javabean;<br/>
	 * 当null==str或str为无效json格式字符串时, 则返回 null.
	 * 
	 * @param str
	 *            json格式字符串.
	 * @return JSONObject json封装对象实例.
	 */
	public static JSONObject toBean(String str) {
		if (StringUtils.hasText(str)) {
			try {
				return JSON.parseObject(str);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将json格式字符串转为指定类型的Javabean;<br/>
	 * 当null==str或str为无效json格式字符串时, 则返回 null.
	 * 
	 * @param str
	 *            json格式字符串.
	 * @param clazz
	 *            对象的Class 类型
	 * @return T 对应Javabean实例.
	 */
	public static <T> T toBean(String str, Class<T> clazz) {
		if (StringUtils.hasText(str)) {
			try {
				return JSON.parseObject(str, clazz);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将json格式字符串转为指定泛型的List;<br/>
	 * 当null==str或str为无效json格式字符串时, 则返回 null.
	 * 
	 * @param str
	 *            json格式字符串.
	 * @param clazz
	 *            对象的Class 类型
	 * @return T 对应泛型的List实例.
	 */
	public static <T> List<T> toList(String str, Class<T> clazz) {
		if (StringUtils.hasText(str)) {
			try {
				return JSON.parseArray(str, clazz);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将json格式字符串转为指定类型的Object;<br/>
	 * 当null==str或str为无效json格式字符串时, 则返回 null.<br/>
	 * 例：User dto = JsonUtil.toOject(json, new TypeReference<User>() {});
	 * 
	 * @param str
	 *            json格式字符串.
	 * @param clazz
	 *            对象的Class 类型
	 * @return T 对应Object实例.
	 */
	public static <T> T toOject(String str, TypeReference<T> type) {
		if (StringUtils.hasText(str)) {
			try {
				return JSON.parseObject(str, type);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 利用Json序列化和反序列化实现任意对象深度克隆.<br/>
	 * 例如: Resources res = JsonUtil.clone(tres, new
	 * TypeReference&ltResources&gt(){});
	 * 
	 * @param <T>
	 * @param obj
	 * @param type
	 * @return
	 */
	public static <T> T clone(Object obj, TypeReference<T> type) {
		if (null != obj) {
			String json = toJson(obj);
			try {
				return JSON.parseObject(json, type);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
