package com.william.model;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer status;
	private Date createTime;
	private Date modifyTime;
	public BaseModel(boolean b) {
		if(b) {
			this.status = 1;
			this.createTime = new Date(System.currentTimeMillis());
			this.modifyTime = new Date(System.currentTimeMillis());
		}
	}
	public BaseModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BaseModel(Integer status, Date createTime, Date modifyTime) {
		super();
		this.status = status;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
