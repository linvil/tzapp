package com.william.service;

import com.william.model.ResponseBean;
import com.william.model.dto.AnnouncementDTO;

public interface AnnouncementService {
	public ResponseBean getAnnouncements(int page,int pageSize);
	public ResponseBean addAnnouncement(AnnouncementDTO announcement);
	public ResponseBean deleteAnnouncement(Integer announcementId);
	public ResponseBean getAnnouncementsDetail(Integer announcementId);
	public ResponseBean updateAnnouncement(AnnouncementDTO announcement);
}
