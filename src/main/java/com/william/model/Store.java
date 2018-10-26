package com.william.model;
// Generated 2018-10-9 19:20:29 by Hibernate Tools 4.3.5.Final

import java.util.Date;

/**
 * Store generated by hbm2java
 */
public class Store implements java.io.Serializable {

	private Integer id;
	private String no;
	private String name;
	private byte status;
	private Date createTime;
	private Date updateTime;
	private String parament;
	private Byte delTf;

	public Store() {
	}

	public Store(String no, byte status) {
		this.no = no;
		this.status = status;
	}

	public Store(String no, String name, byte status, Date createTime, Date updateTime, String parament, Byte delTf) {
		this.no = no;
		this.name = name;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.parament = parament;
		this.delTf = delTf;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getParament() {
		return this.parament;
	}

	public void setParament(String parament) {
		this.parament = parament;
	}

	public Byte getDelTf() {
		return this.delTf;
	}

	public void setDelTf(Byte delTf) {
		this.delTf = delTf;
	}

}