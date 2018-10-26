package com.william.utils;


import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.alibaba.fastjson.serializer.PropertyPreFilter;



@Component
public class ContextUtils {

	private static ThreadLocal<PropertyPreFilter> propertyPreFilter = new ThreadLocal<>();

	@Autowired
	private SessionFactory sessionFactory;

	private void openAndBindSession() {

		// 启动时，currentSession获取不到session
		Session session = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
	}

	@PostConstruct
	public void init() {
		openAndBindSession();

		refresh();
	}

	public static void refresh() {
		
	}

	public static void setPropertyPreFilter(PropertyPreFilter propertyPreFilter) {
		ContextUtils.propertyPreFilter.set(propertyPreFilter);
	}

	public static PropertyPreFilter getPropertyPreFilter() {
		return propertyPreFilter.get();
	}


}
