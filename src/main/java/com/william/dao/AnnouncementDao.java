package com.william.dao;

import java.util.List;

import com.william.model.Announcement;
import com.william.model.dto.FilterDTO;

/**
 * 公告表操作接口
 * @author zhangmingwei
 *
 */
public interface AnnouncementDao extends DaoSupport{
	/**
	 * 分页查询所有有效的公告信息
	 * @param page
	 * @return
	 */
	public List<Announcement> queryByPage(String title,FilterDTO filterEx);
	/**
	 * 查询所有有效公告的数量
	 * @return
	 */
	public int getCount(String title);
	public void addAnnouncement(Announcement announcement);
	public void deleteById(Integer id);
	public Announcement findById(Integer id);
}
