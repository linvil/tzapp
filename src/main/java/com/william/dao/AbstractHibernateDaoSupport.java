package com.william.dao;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.william.model.dto.FilterDTO;

public abstract class AbstractHibernateDaoSupport implements DaoSupport {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public <T> void save(T t) {
		currentSession().save(t);
	}

	@Override
	public <T> void update(T t) {
		currentSession().update(t);
	}
	@Override
	public <T> void delete(T t) {
		currentSession().delete(t);
	}
	public<T> void merge(T t){
		currentSession().merge(t);
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> clazz, Serializable id) {
		return (T) currentSession().get(clazz, id);
	}

	@Override
	public <T> Criteria list(Class<T> clazz) {
		return currentSession().createCriteria(clazz);
	}

	protected void addPage(Criteria criteria, int page, int pageSize) {
		criteria.setFirstResult((page - 1) * pageSize);
		criteria.setMaxResults(pageSize);
	}
	
	protected void addPageBy(Query query, FilterDTO filterEx) {
		if (null != filterEx){
			int page = filterEx.getPage();
			int pageSize = filterEx.getPageSize();
			
			if (0 != page && 0 != pageSize){
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
			}
		}
	}
	
}
