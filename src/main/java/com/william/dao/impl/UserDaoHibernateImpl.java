package com.william.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.william.dao.AbstractHibernateDaoSupport;
import com.william.dao.UserDao;
import com.william.model.Token;
import com.william.model.User;
@Repository
public class UserDaoHibernateImpl extends AbstractHibernateDaoSupport implements UserDao{
	
	
	@Override
	public boolean checkUserNoUniqueness(Integer id, String userNo, Byte status) {
		Criteria criteria = currentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", userNo));

		criteria.add(Restrictions.eq("delTf", (byte)0));
		if (null != status) {
			criteria.add(Restrictions.eq("status", status));
		}

		criteria.addOrder(Order.asc("id"));
		criteria.setMaxResults(2);

		
		List<?> list = criteria.list();
		
		if (list.isEmpty()) {
			return false;
		}

		User first = (User) list.get(0);
		if (id.equals(first.getId())) {
			return false;
		}
		
		return true;
	}
	@SuppressWarnings("unchecked")
	public List<Token> listUserTokens(Integer userId, Byte[] clientTypes, boolean delExpire) {
		Criteria criteria = list(Token.class);

		criteria.add(Restrictions.eq("userId", userId));
		
		if (null != clientTypes && 0 < clientTypes.length) {
			criteria.add(Restrictions.in("clientType", clientTypes));
		}

		Date now = new Date();
		criteria.add(Restrictions.le("createTime", now));
		if (!delExpire) {
			criteria.add(Restrictions.ge("expireTime", now));
		}

		return criteria.list();
	}
	@Override
	public User getUserByUserNo(String loginName, Byte status) {
		Criteria criteria = currentSession().createCriteria(User.class);
		
		criteria.add(Restrictions.eq("username", loginName));	
		criteria.add(Restrictions.eq("delTf", (byte)0));
		if (null != status) {
			criteria.add(Restrictions.eq("status", status));
		}

		criteria.setMaxResults(1);

		List<?> list = criteria.list();

		if (list.isEmpty()) {
			return null;
		}

		return (User) list.get(0);
	}
	@Override
	public Token getUserTokenByTokenId(String tokenId) {
		Criteria criteria = list(Token.class);

		criteria.add(Restrictions.eq("tokenId", tokenId));

		criteria.add(Restrictions.le("createTime", new Date()));
		criteria.add(Restrictions.ge("expireTime", new Date()));
		
		criteria.setMaxResults(1);

		List<Token> list = criteria.list();

		if (list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}
	@Override
	public boolean checkUserNoUniqueness(String userName, int status) {
		Criteria criteria = currentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", userName));
		criteria.add(Restrictions.eq("status", status));

		criteria.setMaxResults(2);

		List<?> list = criteria.list();
		
		if (list.isEmpty()) {
			return false;
		}

		User first = (User) list.get(0);
		if (ObjectUtils.isEmpty(first)) {
			return false;
		}
		return true;
	}
	@Override
	public void updatePwd(String userName, String passWord) {
		StringBuffer hql = new StringBuffer("update user set LoginPassword=:password,modifyTime=:modifyTime where username=:username and status=1");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("password", passWord);
		param.put("modifyTime", new Date(System.currentTimeMillis()));
		param.put("username", userName);
		Query query = this.currentSession().createSQLQuery(hql.toString());
		query.setProperties(param);
		query.executeUpdate();
	}
	@Override
	public void updatePayPassword(String userName, String payPassWord,String saltPay) {
		StringBuffer hql = new StringBuffer("update user set payPassword=:payPassword,modifyTime=:modifyTime ");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("payPassword", payPassWord);
		param.put("modifyTime", new Date(System.currentTimeMillis()));
		if(null!=saltPay&&!"".equals(saltPay)) {
			hql.append(" ,saltPay=:saltPay ");
			param.put("saltPay", saltPay);
		}
		hql.append(" where username=:username and status=1");
		param.put("username", userName);
		Query query = this.currentSession().createSQLQuery(hql.toString());
		query.setProperties(param);
		query.executeUpdate();
	}
	@Override
	public void updateNickname(String userName, String nickName) {
		StringBuffer hql = new StringBuffer("update user set nickName=:nickName,modifyTime=:modifyTime where username=:username and status=1");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("nickName", nickName);
		param.put("modifyTime", new Date(System.currentTimeMillis()));
		param.put("username", userName);
		Query query = this.currentSession().createSQLQuery(hql.toString());
		query.setProperties(param);
		query.executeUpdate();
	}

	
}
