package com.william.model;
// Generated 2018-10-17 18:32:00 by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;

/**
 * SysPermission generated by hbm2java
 */
public class SysPermission implements java.io.Serializable {

	private int id;
	private String pdesc;
	private String name;
	private Integer menuId;
	private Set sysOperations = new HashSet(0);

	public SysPermission() {
	}

	public SysPermission(int id) {
		this.id = id;
	}

	public SysPermission(int id, String pdesc, String name, Integer menuId, Set sysOperations) {
		this.id = id;
		this.pdesc = pdesc;
		this.name = name;
		this.menuId = menuId;
		this.sysOperations = sysOperations;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPdesc() {
		return this.pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Set getSysOperations() {
		return this.sysOperations;
	}

	public void setSysOperations(Set sysOperations) {
		this.sysOperations = sysOperations;
	}

}