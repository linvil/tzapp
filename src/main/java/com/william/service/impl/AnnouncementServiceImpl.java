package com.william.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.william.dao.AnnouncementDao;
import com.william.exception.BusinessException;
import com.william.model.Announcement;
import com.william.model.ResponseBean;
import com.william.model.dto.AnnouncementDTO;
import com.william.model.dto.FilterDTO;
import com.william.model.enum_.ErrorCodes;
import com.william.service.AnnouncementService;
import com.william.utils.BeanUtils;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
	@Autowired
	private AnnouncementDao announcementDao;
	@Override
	public ResponseBean getAnnouncements(int page, int pageSize) {
		ResponseBean resp = new ResponseBean();
		int total = this.announcementDao.getCount(null);
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setPage(page);
		filterDTO.setPageSize(pageSize);
		List<Announcement> list = announcementDao.queryByPage(null, filterDTO);
		BeanUtils.copyAs(list, AnnouncementDTO.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", total);
		map.put("announcements", list);
		resp.setData(map);
		resp.setError("成功");
		return resp;
	}

	public ResponseBean addAnnouncement(AnnouncementDTO announcement) {
		Announcement newAnnouncement = new Announcement(true);
		BeanUtils.copyPropertiesIgnoreNull(announcement, newAnnouncement);
		this.announcementDao.save(newAnnouncement);
		ResponseBean resp = new ResponseBean();
		return resp;
	}

	@Override
	public ResponseBean deleteAnnouncement(Integer announcementId) {
		ResponseBean resp = new ResponseBean();
//		Announcement announcement = this.announcementDao.findById(announcementId);
		havaAnnouncement(announcementId);
		this.announcementDao.deleteById(announcementId);
		return resp;
	}

	@Override
	public ResponseBean getAnnouncementsDetail(Integer announcementId) {
		ResponseBean resp = new ResponseBean();
		Announcement announcement = this.announcementDao.findById(announcementId);
		if(ObjectUtils.isEmpty(announcement)) {
			throw new BusinessException(ErrorCodes.INFORMATION_NOT_EXIST.getCode(),ErrorCodes.INFORMATION_NOT_EXIST.getMsg());
		}
		resp.setData(announcement);
		return resp;
	}

	@Override
	public ResponseBean updateAnnouncement(AnnouncementDTO announcement) {
		havaAnnouncement(announcement.getId());
		this.announcementDao.update(announcement);
		ResponseBean resp = new ResponseBean();
		return resp;
	}

	public void havaAnnouncement(Integer announcementId) {
		Announcement announcement = this.announcementDao.findById(announcementId);
		if(ObjectUtils.isEmpty(announcement)) {
			throw new BusinessException(ErrorCodes.INFORMATION_NOT_EXIST.getCode(),ErrorCodes.INFORMATION_NOT_EXIST.getMsg());
		}
	}
}
