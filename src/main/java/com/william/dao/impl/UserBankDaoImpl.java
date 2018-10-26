package com.william.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.william.dao.AbstractHibernateDaoSupport;
import com.william.dao.UserBankDao;
import com.william.model.UserBank;

@Component
public class UserBankDaoImpl extends AbstractHibernateDaoSupport implements UserBankDao{

	@Override
	public UserBank findUserBankcard(Integer userId, String bankCard) {
		StringBuffer hql = new StringBuffer("select * from user_bank where userId=:userId and bankCode=:bankCode and status=1");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("bankCode", bankCard);
		Query query = this.currentSession().createSQLQuery(hql.toString()).addEntity(UserBank.class);
		query.setProperties(map);
		@SuppressWarnings("unchecked")
		List<UserBank> list = query.list();
		if(ObjectUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBank> findByUserId(Integer userId) {
		StringBuffer hql = new StringBuffer("select * from user_bank where userId=:userId and status=1");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		Query query = this.currentSession().createSQLQuery(hql.toString()).addEntity(UserBank.class);
		query.setProperties(map);
		return query.list();
	}

}
