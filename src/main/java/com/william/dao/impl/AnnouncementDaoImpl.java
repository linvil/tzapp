package com.william.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.william.dao.AbstractHibernateDaoSupport;
import com.william.dao.AnnouncementDao;
import com.william.model.Announcement;
import com.william.model.dto.FilterDTO;
@Repository
public class AnnouncementDaoImpl extends AbstractHibernateDaoSupport implements AnnouncementDao{

	@Override
	public void addAnnouncement(Announcement announcement) {
		this.save(announcement);
	}


	@Override
	public int getCount(String title) {
		StringBuffer hql = new StringBuffer("select * from announcement where status = 1");
		Map<String,Object> param = new HashMap<String,Object>();
		if(null != title) {
			hql = hql.append(" and match (".concat(title).concat(") against (:keyword in boolean mode)"));
			param.put("keyword", title);
		}
		Query query = currentSession().createSQLQuery(hql.toString());
		query.setProperties(param);
		return query.list().size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Announcement> queryByPage(String title , FilterDTO filterEx) {
		StringBuffer hql = new StringBuffer("select * from announcement where status = 1");
		Map<String,Object> param = new HashMap<String,Object>();
		if(null != title) {
			hql = hql.append(" and match (".concat(title).concat(") against (:keyword in boolean mode)"));
			param.put("keyword", title);
		}
		hql.append(" order by createTime desc");
		Query query = currentSession().createSQLQuery(hql.toString()).addEntity(Announcement.class);
		query.setProperties(param);
		addPageBy(query, filterEx);
		return query.list();
	}


	@Override
	public void deleteById(Integer id) {
		Map<String,Object> param = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("delete from announcement where id = :id");
		param.put("id", id);
		Query query = this.currentSession().createSQLQuery(hql.toString());
		query.setProperties(param);
		query.executeUpdate();
	}


	@Override
	public Announcement findById(Integer id) {
		Map<String,Object> param = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select * from announcement where id = :id");
		param.put("id", id);
		Query query = this.currentSession().createSQLQuery(hql.toString()).addEntity(Announcement.class);
		query.setProperties(param);
		@SuppressWarnings("unchecked")
		List<Announcement> list = query.list();
		if(ObjectUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

}
