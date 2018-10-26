package com.william.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import com.william.exception.BeanException;

public class CollectionUtils {

	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> Collection<T> sort(Collection<T> collect) {
		T[] arrT = (T[]) collect.toArray();

		Arrays.sort(arrT);

		return Arrays.asList(arrT);
	}

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> sort(Collection<T> collect, Comparator<T> comparator) {
		T[] arrT = (T[]) collect.toArray();

		Arrays.sort(arrT, comparator);

		return Arrays.asList(arrT);
	}

	public static <T> Map<Object, List<T>> mapList(List<T> list, String... propertyNames) {
		Map<Object, List<T>> map = new HashMap<>();

		for (T t : list) {
			String key = "";
			try {
				for (int i = 0; i < propertyNames.length; i++) {
					key += String.valueOf(PropertyUtils.getProperty(t, propertyNames[i])) + "||";
				}
			} catch (Exception e) {
				throw new BeanException("获取属性值出错：" + propertyNames);
			}

			List<T> items = map.get(key);

			if (items == null) {
				items = new ArrayList<>();
				map.put(key, items);
			}

			items.add(t);
		}

		return map;
	}
	
	public static <T> Map<Object, List<T>> mapListByUniqueParam(List<T> list, String propertyName) {
		Map<Object, List<T>> map = new HashMap<>();
		for (T t : list) {
			String key = "";
			try {
				key += String.valueOf(PropertyUtils.getProperty(t, propertyName));
			} catch (Exception e) {
				throw new BeanException("获取属性值出错：" + propertyName);
			}

			List<T> items = map.get(key);
			if (items == null) {
				items = new ArrayList<>();
				map.put(key, items);
			}
			items.add(t);
		}

		return map;
	}

	/**
	 * 按属性值分组，每个分组取1条记录
	 * 
	 * @param list
	 * @param propertyName
	 * @return
	 */
	public static <T> List<T> listFirstByProperty(List<T> list, String propertyName) {
		List<T> newList = new ArrayList<>();

		Set<Object> set = new HashSet<>();

		for (T t : list) {
			Object key;
			try {
				key = PropertyUtils.getProperty(t, propertyName);
			} catch (Exception e) {
				throw new BeanException("获取属性值出错：" + propertyName);
			}

			if (set.contains(key)) {
				continue;
			}

			set.add(key);

			newList.add(t);
		}

		return newList;
	}

}
