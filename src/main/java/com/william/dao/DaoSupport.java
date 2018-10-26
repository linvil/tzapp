package com.william.dao;

import java.io.Serializable;

import org.hibernate.Criteria;


public interface DaoSupport {

    public <T> void save(T t);

    public <T> void update(T t);

    public <T> void delete(T t);

    public <T> T get(Class<T> clazz, Serializable id);

    public <T> void merge(T t);
    
    public <T> Criteria list(Class<T> clazz);
}
