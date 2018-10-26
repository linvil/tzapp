package com.william.controller.announcement;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.william.model.ResponseBean;
import com.william.model.dto.AnnouncementDTO;
import com.william.service.AnnouncementService;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
	private static Logger logger = Logger.getLogger(AnnouncementController.class);
	@Autowired
	private AnnouncementService announcementService;
	/**
	 * 获取公告信息
	 * @return
	 */
	@RequestMapping("/getAnnouncements")
	@ResponseBody
	public ResponseBean getAnnouncements(int page,int pageSize) {
		logger.debug("获取公告信息!");
		ResponseBean resp = announcementService.getAnnouncements(page, pageSize);
		return resp;
	}
	
	/**
	 * 添加公告信息
	 * @return
	 */
	@RequestMapping("/addAnnouncement")
	@ResponseBody
	public ResponseBean addAnnouncement(AnnouncementDTO announcement) {
		ResponseBean resp = announcementService.addAnnouncement(announcement);
		return resp;
	}
	/**
	 * 删除公告信息
	 * @return
	 */
	@RequestMapping("/deleteAnnouncement")
	@ResponseBody
	public ResponseBean deleteAnnouncement(Integer announcementId) {
		ResponseBean resp = announcementService.deleteAnnouncement(announcementId);
		return resp;
	}
	/**
	 * 获取公告详情
	 * @param announcementId
	 * @return
	 */
	@RequestMapping("/getAnnouncementDetail")
	@ResponseBody
	public ResponseBean getAnnouncementDetail(Integer announcementId) {
		return this.announcementService.getAnnouncementsDetail(announcementId);
	}
	/**
	 * 修改公告信息
	 * @return
	 */
	@RequestMapping("/updateAnnouncement")
	@ResponseBody
	public ResponseBean updateAnnouncement(AnnouncementDTO announcement) {
		return null;
	}
}
