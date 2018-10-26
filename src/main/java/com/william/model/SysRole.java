package com.william.model;
// Generated 2018-10-17 18:32:00 by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;

/**
 * SysRole generated by hbm2java
 */
public class SysRole implements java.io.Serializable {

	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private String role;
	private Set sysUsers = new HashSet(0);
	private Set sysRolePermissions = new HashSet(0);

	public SysRole() {
	}

	public SysRole(String roleName, String roleDesc, String role, Set sysUsers, Set sysRolePermissions) {
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.role = role;
		this.sysUsers = sysUsers;
		this.sysRolePermissions = sysRolePermissions;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(Set sysUsers) {
		this.sysUsers = sysUsers;
	}

	public Set getSysRolePermissions() {
		return this.sysRolePermissions;
	}

	public void setSysRolePermissions(Set sysRolePermissions) {
		this.sysRolePermissions = sysRolePermissions;
	}

}
