package com.william.model;
// Generated 2018-10-17 18:32:00 by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;

/**
 * SysOperation generated by hbm2java
 */
public class SysOperation implements java.io.Serializable {

	private int id;
	private String desc;
	private String name;
	private String operation;
	private Set sysPermissions = new HashSet(0);

	public SysOperation() {
	}

	public SysOperation(int id) {
		this.id = id;
	}

	public SysOperation(int id, String desc, String name, String operation, Set sysPermissions) {
		this.id = id;
		this.desc = desc;
		this.name = name;
		this.operation = operation;
		this.sysPermissions = sysPermissions;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Set getSysPermissions() {
		return this.sysPermissions;
	}

	public void setSysPermissions(Set sysPermissions) {
		this.sysPermissions = sysPermissions;
	}

}