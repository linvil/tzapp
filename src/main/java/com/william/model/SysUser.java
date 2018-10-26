package com.william.model;
// Generated 2018-10-17 18:32:00 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SysUser generated by hbm2java
 */
public class SysUser implements java.io.Serializable {

	private Integer id;
	private String username;
	private String password;
	private String phone;
	private String email;
	private String mark;
	private Date lastLogin;
	private String loginIp;
	private String imageUrl;
	private Byte locked;
	private String salt;
	private Byte delTf;
	private Set sysRoles = new HashSet(0);

	public SysUser() {
	}

	public SysUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public SysUser(String username, String password, String phone, String email, String mark, Date lastLogin,
			String loginIp, String imageUrl, Byte locked, String salt, Byte delTf, Set sysRoles) {
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.mark = mark;
		this.lastLogin = lastLogin;
		this.loginIp = loginIp;
		this.imageUrl = imageUrl;
		this.locked = locked;
		this.salt = salt;
		this.delTf = delTf;
		this.sysRoles = sysRoles;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Byte getLocked() {
		return this.locked;
	}

	public void setLocked(Byte locked) {
		this.locked = locked;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Byte getDelTf() {
		return this.delTf;
	}

	public void setDelTf(Byte delTf) {
		this.delTf = delTf;
	}

	public Set getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(Set sysRoles) {
		this.sysRoles = sysRoles;
	}

}