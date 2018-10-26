package com.william.model.dto;

import java.util.Date;

public class FilterDTO {
	
	private String sumParam;
	private String searchParam;
	private String keyword;
	private String orders;
	private int page;
	private int pageSize;
	private Double userLongitude;
	private Double userLatitude;
	private Date beginTime;
	private Date endTime;
	
	
	public static final Integer DEFUALT_PAGE = 0;
	public static final Integer DEFUALT_PAGESIZE = 20;
	
	public FilterDTO() {
		this.orders = "";
	}
	
	public String getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}	
	
	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}	
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}	
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}	

	public Double getUserLongitude() {
		return userLongitude;
	}

	public void setUserLongitude(Double userLongitude) {
		this.userLongitude = userLongitude;
	}

	public Double getUserLatitude() {
		return userLatitude;
	}

	public void setUserLatitude(Double userLatitude) {
		this.userLatitude = userLatitude;
	}

	public String getSumParam() {
		return sumParam;
	}

	public void setSumParam(String sumParam) {
		this.sumParam = sumParam;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	
}
